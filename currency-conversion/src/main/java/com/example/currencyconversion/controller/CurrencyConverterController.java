package com.example.currencyconversion.controller;

import com.example.currencyconversion.service.CurrencyConverterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConverterController {

    private final CurrencyConverterService converterService;

    public CurrencyConverterController(CurrencyConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping("/from/{from}/to/{to}")
    public BigDecimal converterCurrency(@PathVariable String from, @PathVariable String to){
        return converterService.convert(from,  to);
    }

}
