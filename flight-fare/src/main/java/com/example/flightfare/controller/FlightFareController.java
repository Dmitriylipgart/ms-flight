package com.example.flightfare.controller;

import com.example.flightfare.entity.FlightFare;
import com.example.flightfare.proxy.CurrencyConversionServiceProxy;
import com.example.flightfare.repository.FlightFareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
public class FlightFareController {

    private final FlightFareRepository repository;
    private final RestTemplate restTemplate;
    private final CurrencyConversionServiceProxy feignProxy;

    @Value("${currency.base:USD}")
    private String baseCurrency;

    public FlightFareController(FlightFareRepository repository, RestTemplate template, CurrencyConversionServiceProxy feignProxy) {
        this.repository = repository;
        this.restTemplate = template;
        this.feignProxy = feignProxy;
    }

    @GetMapping("/{flightCode}/fare/{currency}")
    public FlightFare getSingleTicketFare(@PathVariable String flightCode, @PathVariable String currency) {
        FlightFare fare = getFlightFare(flightCode);
        fare.setCurrency(currency);
        if (!baseCurrency.equals(currency)) {
            BigDecimal conversionRate = this.getConversion(currency);
            BigDecimal convertedFare = fare.getFare().multiply(conversionRate);
            fare.setFare(convertedFare);
        }
        return fare;
    }

    private FlightFare getFlightFare(String flightCode) {
        FlightFare flightFare = new FlightFare(null, flightCode, null, null);
        return repository.findOne(Example.of(flightFare)).get();
    }

//    private BigDecimal getConversion(String toCurrency){
//
//        String uri = "http://currency/api/currency/from/{from}/to/{to}";
//
//        Map<String, String> urlPathVariables = new HashMap<>();
//        urlPathVariables.put("from", baseCurrency);
//        urlPathVariables.put("to", toCurrency);
//        ResponseEntity<BigDecimal> responseEntity = restTemplate.getForEntity(uri, BigDecimal.class, urlPathVariables);
//        return responseEntity.getBody();
//    }

    private BigDecimal getConversion(String toCurrency) {

        return feignProxy.convertCurrency(baseCurrency, toCurrency);
    }


}
