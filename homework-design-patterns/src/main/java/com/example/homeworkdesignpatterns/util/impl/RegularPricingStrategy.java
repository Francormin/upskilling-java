package com.example.homeworkdesignpatterns.util.impl;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.util.PricingStrategy;

public class RegularPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(Product product) {
        return product.getPrice();
    }

}
