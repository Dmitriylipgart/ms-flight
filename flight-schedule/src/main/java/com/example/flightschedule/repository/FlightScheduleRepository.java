package com.example.flightschedule.repository;

import com.example.flightschedule.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightScheduleRepository extends JpaRepository<Flight, Long> {

}
