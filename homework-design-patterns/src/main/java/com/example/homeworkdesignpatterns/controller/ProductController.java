package com.example.homeworkdesignpatterns.controller;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.util.PricingService;
import com.example.homeworkdesignpatterns.util.impl.DiscountPricingStrategy;
import com.example.homeworkdesignpatterns.util.impl.RegularPricingStrategy;
import com.example.homeworkdesignpatterns.service.facade.ProductFacade;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductFacade productFacade;
    private final PricingService pricingService;

    public ProductController(ProductFacade productFacade, PricingService pricingService) {
        this.productFacade = productFacade;
        this.pricingService = pricingService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productFacade.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productFacade.getProductById(id);
    }

    @PostMapping
    public void createProduct(@RequestBody Product producto) {
        productFacade.createProduct(producto);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product producto) {
        productFacade.updateProduct(id, producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productFacade.deleteProduct(id);
    }

    @GetMapping("/price/{id}")
    public double getProductPrice(@PathVariable Long id) {
        Product product = productFacade.getProductById(id);
        return pricingService.calculatePrice(product);
    }

    @PostMapping("/price")
    public void setPricingStrategy(@RequestParam String strategy) {
        if (strategy.equalsIgnoreCase("regular")) {
            pricingService.setStrategy(new RegularPricingStrategy());
        } else if (strategy.equalsIgnoreCase("discount")) {
            pricingService.setStrategy(new DiscountPricingStrategy(0.1));
        }
    }

}
