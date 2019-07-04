package com.example.flightfare.controller;

import com.example.flightfare.entity.FlightFare;
import com.example.flightfare.service.FlightFareService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FlightFareController {


    private final RestTemplate restTemplate;
    private final FlightFareService flightFareService;


    @Value("${currency.base:USD}")
    private String baseCurrency;

    public FlightFareController(RestTemplate template, FlightFareService flightFareService) {
        this.restTemplate = template;
        this.flightFareService = flightFareService;
    }

    @GetMapping("/{flightCode}/fare/{currency}")
    public FlightFare getSingleTicketFare(@PathVariable String flightCode, @PathVariable String currency) {
       return flightFareService.getFlightFare(flightCode, currency);
    }




}
