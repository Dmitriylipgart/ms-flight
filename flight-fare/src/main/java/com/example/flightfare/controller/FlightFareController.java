package com.example.flightfare.controller;

import com.example.flightfare.entity.FlightFare;
import com.example.flightfare.repository.FlightFareRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flight")
public class FlightFareController {

    private final FlightFareRepository repository;
    private final EurekaClient eurekaClient;

    @Value("${eureka.client.use:false}")
    private boolean useEurekaClient;

    @Value("${currency.base:USD}")
    private String baseCurrency;

    public FlightFareController(FlightFareRepository repository, EurekaClient eurekaClient) {
        this.repository = repository;

        this.eurekaClient = eurekaClient;
    }

    @GetMapping("/{flightCode}/fare/{currency}")
    public FlightFare getSingleTicketFare(@PathVariable String flightCode, @PathVariable String currency){
        FlightFare fare = getFlightFare(flightCode);
        fare.setCurrency(currency);
        if(!baseCurrency.equals(currency)){
            BigDecimal conversionRate = this.getConversion(currency);
            BigDecimal convertedFare = fare.getFare().multiply(conversionRate);
            fare.setFare(convertedFare);
        }
        return  fare;
    }

    private FlightFare getFlightFare(String flightCode){
        FlightFare flightFare = new FlightFare(null, flightCode, null, null);
        return repository.findOne(Example.of(flightFare)).get();
    }

    private BigDecimal getConversion(String toCurrency){

        String uri;

        if(useEurekaClient){
            Application app = eurekaClient.getApplication("currency");
            List<InstanceInfo> instances = app.getInstances();
            String serviceUri = instances.get(0).getHomePageUrl();
            uri = String.format("%s/api/currency/from/{from}/to/{to}", serviceUri);

        }else{
            uri = "http://localhost:8085/api/currency/from/{from}/to/{to}";
        }

        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        urlPathVariables.put("from", baseCurrency);
        urlPathVariables.put("to", toCurrency);
        ResponseEntity<BigDecimal> responseEntity = template.getForEntity(uri, BigDecimal.class, urlPathVariables);
        return responseEntity.getBody();
    }


}
