package com.example.homeworktestintegracion.service.impl;

import com.example.homeworktestintegracion.entity.Product;
import com.example.homeworktestintegracion.entity.ShoppingCart;
import com.example.homeworktestintegracion.exception.ResourceNotFoundException;
import com.example.homeworktestintegracion.repository.ProductRepository;
import com.example.homeworktestintegracion.repository.ShoppingCartRepository;
import com.example.homeworktestintegracion.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProductToCart(Long cartId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).
            orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cart.addProduct(product);
        shoppingCartRepository.save(cart);
    }

}
