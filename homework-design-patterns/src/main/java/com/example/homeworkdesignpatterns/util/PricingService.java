package com.example.homeworkdesignpatterns.util;

import com.example.homeworkdesignpatterns.entity.Product;

import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private PricingStrategy pricingStrategy;

    public void setStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculatePrice(Product product) {
        return pricingStrategy.calculatePrice(product);
    }

}
