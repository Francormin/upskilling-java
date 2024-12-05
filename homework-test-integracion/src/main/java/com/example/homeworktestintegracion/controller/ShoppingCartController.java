package com.example.homeworktestintegracion.controller;

import com.example.homeworktestintegracion.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ProductService productService;

    @PostMapping("/{cartId}/add/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        productService.addProductToCart(cartId, productId);
        return ResponseEntity.ok().build();
    }

}
