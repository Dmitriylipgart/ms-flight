package com.example.flightschedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue
    private Long id;
    private String fightCode;
    private String flightFrom;
    private String flightTo;
    private String airline;
    private String departureTime;
    private String arrivalTime;

}
