package com.example.flightfare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionDTO {

    private String currencyFrom;
    private String currencyTo;
    private BigDecimal conversionRate;

}
