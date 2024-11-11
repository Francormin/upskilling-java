package com.example.homeworkmvc.service.impl;

import com.example.homeworkmvc.repository.ConversionRepository;

import com.example.homeworkmvc.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    private ConversionRepository conversionRepository;

    public void addCurrency(String currencyCode, Double rate) {
        conversionRepository.addConversionRate(currencyCode, rate);
    }

    public double convert(double amount, String sourceCurrency, String targetCurrency) throws Exception {
        Double sourceRate = conversionRepository.getConversionRate(sourceCurrency);
        Double targetRate = conversionRepository.getConversionRate(targetCurrency);

        if (sourceRate == null || targetRate == null) {
            throw new Exception("One or both currencies are not available.");
        }

        // Convert the amount from source to base currency (USD), then to the target currency
        double amountInUSD = amount / sourceRate;
        return amountInUSD * targetRate;
    }

}
