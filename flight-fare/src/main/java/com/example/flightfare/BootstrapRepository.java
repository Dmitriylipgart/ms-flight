package com.example.flightfare;

import com.example.flightfare.entity.FlightFare;
import com.example.flightfare.repository.FlightFareRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BootstrapRepository implements CommandLineRunner {

    private FlightFareRepository flightFareRepository;

    public BootstrapRepository(FlightFareRepository flightFareRepository) {
        super();
        this.flightFareRepository = flightFareRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        FlightFare fare1 = new FlightFare("UL-191", BigDecimal.valueOf(1000));
        FlightFare fare2 = new FlightFare("UL-192", BigDecimal.valueOf(2000));
        FlightFare fare3 = new FlightFare("UL-193", BigDecimal.valueOf(3000));
        FlightFare fare4 = new FlightFare("UL-194", BigDecimal.valueOf(4000));

        flightFareRepository.save(fare1);
        flightFareRepository.save(fare2);
        flightFareRepository.save(fare3);
        flightFareRepository.save(fare4);
    }

}
