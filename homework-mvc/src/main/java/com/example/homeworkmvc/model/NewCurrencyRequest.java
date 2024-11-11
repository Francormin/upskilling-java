package com.example.homeworkmvc.model;

public class NewCurrencyRequest {

    private String sourceCurrency;
    private Double amount;

    public NewCurrencyRequest() {
    }

    public NewCurrencyRequest(String sourceCurrency, Double amount) {
        this.sourceCurrency = sourceCurrency;
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
