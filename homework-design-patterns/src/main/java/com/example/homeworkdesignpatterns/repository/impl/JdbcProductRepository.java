package com.example.homeworkdesignpatterns.repository.impl;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.repository.ProductRepository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM products WHERE id = ?",
            new Object[]{id},
            new BeanPropertyRowMapper<>(Product.class)
        );
    }

    @Override
    public int save(Product product) {
        return jdbcTemplate.update(
            "INSERT INTO products (name, price) VALUES (?, ?)",
            product.getName(),
            product.getPrice()
        );
    }

    @Override
    public int update(Product product) {
        return jdbcTemplate.update(
            "UPDATE products SET name = ?, price = ? WHERE id = ?",
            product.getName(),
            product.getPrice(),
            product.getId()
        );
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }

}
