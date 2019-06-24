package com.example.flightfare.repository;

import com.example.flightfare.entity.FlightFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightFareRepository extends JpaRepository<FlightFare, Long> {
}
