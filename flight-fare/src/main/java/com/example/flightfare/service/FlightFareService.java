package com.example.flightfare.service;

import com.example.flightfare.entity.FlightFare;

public interface FlightFareService {
    FlightFare getFlightFare(String flightCode, String currency);
}
