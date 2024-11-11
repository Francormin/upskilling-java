package com.example.homeworkmvc.service;

public interface ConversionService {

    void addCurrency(String currencyCode, Double rate);

    double convert(double amount, String sourceCurrency, String targetCurrency) throws Exception;

}
