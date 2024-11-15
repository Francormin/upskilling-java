package com.example.homeworkdesignpatterns.service.impl;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.util.PricingService;
import com.example.homeworkdesignpatterns.repository.ProductRepository;
import com.example.homeworkdesignpatterns.service.ProductService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PricingService pricingService;

    public ProductServiceImpl(ProductRepository productRepository, PricingService pricingService) {
        this.productRepository = productRepository;
        this.pricingService = pricingService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public double getProductPrice(Product product) {
        return pricingService.calculatePrice(product);
    }

}
