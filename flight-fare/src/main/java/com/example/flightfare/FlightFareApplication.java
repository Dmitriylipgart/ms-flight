package com.example.flightfare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.example.flightfare")
public class FlightFareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightFareApplication.class, args);
    }

}
