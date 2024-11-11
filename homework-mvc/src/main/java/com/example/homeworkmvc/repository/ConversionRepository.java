package com.example.homeworkmvc.repository;

public interface ConversionRepository {

    Double getConversionRate(String currencyCode);

    void addConversionRate(String currencyCode, Double rate);

}
