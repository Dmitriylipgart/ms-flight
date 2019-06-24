package com.example.currencyconversion.service;

import java.math.BigDecimal;

public interface CurrencyConverterService {
    BigDecimal convert(String from, String to);
}
