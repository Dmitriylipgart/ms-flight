package com.example.flightfare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightFare {
    @Id
    @GeneratedValue
    private Long id;
    private String flightCode;
    private BigDecimal fare;
    private String currency;

    public FlightFare(String flightCode, BigDecimal fare) {
        this.flightCode = flightCode;
        this.fare = fare;
    }
}
