package com.example.flightschedule.controller;

import com.example.flightschedule.entity.Flight;
import com.example.flightschedule.service.FlightScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightScheduleController {

    private final FlightScheduleService service;

    public FlightScheduleController(FlightScheduleService service) {
        this.service = service;
    }

    @GetMapping("/from/{from}/to/{to}")
    public List<Flight> getFlights(@PathVariable String from, @PathVariable String to){
        return service.getFlights(from, to);
    }
}
