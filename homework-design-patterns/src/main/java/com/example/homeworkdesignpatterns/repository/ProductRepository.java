package com.example.homeworkdesignpatterns.repository;

import com.example.homeworkdesignpatterns.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(Long id);

    int save(Product producto);

    int update(Product producto);

    int deleteById(Long id);

}
