package com.example.homeworkdesignpatterns.util;

import com.example.homeworkdesignpatterns.entity.Product;

public interface PricingStrategy {

    double calculatePrice(Product product);

}
