package com.example.flightfare.service;

import com.example.flightfare.entity.FlightFare;
import com.example.flightfare.proxy.CurrencyConversionServiceProxy;
import com.example.flightfare.repository.FlightFareRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FlightFareServiceImpl implements FlightFareService {


    private final CurrencyConversionServiceProxy feignProxy;
    private final FlightFareRepository repository;

    @Value("${currency.base:USD}")
    private String baseCurrency;


    public FlightFareServiceImpl(CurrencyConversionServiceProxy feignProxy, FlightFareRepository repository) {
        this.feignProxy = feignProxy;
        this.repository = repository;
    }

    @HystrixCommand
    public FlightFare getFlightFare(String flightCode, String currency) {
        FlightFare filter = new FlightFare(null, flightCode, null, null);
        FlightFare fare = repository.findOne(Example.of(filter)).get();
        fare.setCurrency(currency);
        if (!baseCurrency.equals(currency)) {
            BigDecimal conversionRate = this.getConversion(currency);
            BigDecimal convertedFare = fare.getFare().multiply(conversionRate);
            fare.setFare(convertedFare);
        }
        return fare;
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
