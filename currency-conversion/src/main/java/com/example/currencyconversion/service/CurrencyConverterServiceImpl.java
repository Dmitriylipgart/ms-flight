package com.example.currencyconversion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Slf4j
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate template;

    @Value("${converter.url}")
    private String converterUrl;

    public CurrencyConverterServiceImpl(RestTemplate template) {
        this.template = template;
    }

    @Override
    public BigDecimal convert(String from, String to) {
        String url = String.format(converterUrl, from, to);
        ResponseEntity<String> forEntity = template.getForEntity(url, String.class);
        String json = forEntity.getBody();
        return this.getConversionRate(json, to);
    }

    private BigDecimal getConversionRate(String json, String to) {

        BigDecimal conversionRate = null;
        try {
            JsonNode node = objectMapper.readTree(json);
            JsonNode ratesNode = node.get("rates");
            conversionRate = ratesNode.get(to).decimalValue();

        } catch (IOException e) {
            log.error("Json parse error: ", e);
        }
        return conversionRate;
    }
}
