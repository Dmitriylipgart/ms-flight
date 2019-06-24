package com.example.flightschedule.service;

import com.example.flightschedule.entity.Flight;
import com.example.flightschedule.repository.FlightScheduleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightScheduleServiceImpl implements FlightScheduleService{
    @Value("${airline.disabled}")
    private String airlineDisabled;

    private final FlightScheduleRepository repository;

    public FlightScheduleServiceImpl(FlightScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Flight> getFlights(String from, String to) {
        Flight filterFlight = new Flight();
        filterFlight.setFlightFrom(from);
        filterFlight.setFlightTo(to);
        Example<Flight> filter = Example.of(filterFlight);
        List<Flight> flights = repository.findAll(filter);
        if(!CollectionUtils.isEmpty(flights)){
            flights = flights.stream().filter(flight -> !airlineDisabled.equals(flight.getAirline()))
                    .collect(Collectors.toList());
        }
        return flights;
    }
}
