package com.example.homeworkmvc.model;

public class ConversionRequest {

    private Double amount;
    private String sourceCurrency;
    private String targetCurrency;

    public ConversionRequest() {
    }

    public ConversionRequest(Double amount, String sourceCurrency, String targetCurrency) {
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public String toString() {
        return "ConversionRequest{" +
            "amount=" + amount +
            ", sourceCurrency='" + sourceCurrency + '\'' +
            ", targetCurrency='" + targetCurrency + '\'' +
            '}';
    }

}
