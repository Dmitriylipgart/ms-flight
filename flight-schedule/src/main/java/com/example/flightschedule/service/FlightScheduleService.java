package com.example.flightschedule.service;

import com.example.flightschedule.entity.Flight;

import java.util.List;

public interface FlightScheduleService {

    List<Flight> getFlights(String from, String to);
}
