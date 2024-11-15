package com.example.homeworkdesignpatterns.util.impl;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.util.PricingStrategy;

public class DiscountPricingStrategy implements PricingStrategy {

    private final double discount;

    public DiscountPricingStrategy(double discount) {
        this.discount = discount;
    }

    @Override
    public double calculatePrice(Product product) {
        return product.getPrice() * (1 - discount);
    }

}
