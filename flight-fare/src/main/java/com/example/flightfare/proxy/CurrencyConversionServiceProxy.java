package com.example.flightfare.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@FeignClient(name="currency")
@RequestMapping("/api")
public interface CurrencyConversionServiceProxy {

    @GetMapping("/from/{from}/to/{to}")
    BigDecimal convertCurrency(@PathVariable String from, @PathVariable String to);
}
