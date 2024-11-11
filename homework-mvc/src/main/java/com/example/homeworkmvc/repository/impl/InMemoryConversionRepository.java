package com.example.homeworkmvc.repository.impl;

import com.example.homeworkmvc.repository.ConversionRepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryConversionRepository implements ConversionRepository {

    // Conversion rates relative to USD
    private final Map<String, Double> conversionRates = new HashMap<>();

    public InMemoryConversionRepository() {
        // Initialize with some default rates relative to USD
        conversionRates.put("USD", 1.0); // USD as the base
        conversionRates.put("EUR", 0.85);
        conversionRates.put("GBP", 0.75);
        conversionRates.put("JPY", 110.0);
        conversionRates.put("MXN", 20.0);
    }

    @Override
    public Double getConversionRate(String currencyCode) {
        return conversionRates.get(currencyCode.toUpperCase());
    }

    @Override
    public void addConversionRate(String currencyCode, Double rate) {
        conversionRates.put(currencyCode.toUpperCase(), rate);
    }

}
