package com.example.homeworkdesignpatterns.service;

import com.example.homeworkdesignpatterns.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Long id);

}
