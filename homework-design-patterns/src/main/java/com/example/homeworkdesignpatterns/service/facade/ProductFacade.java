package com.example.homeworkdesignpatterns.service.facade;

import com.example.homeworkdesignpatterns.entity.Product;
import com.example.homeworkdesignpatterns.service.ProductService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFacade {

    private final ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productService.getProductById(id);
    }

    public void createProduct(Product product) {
        productService.saveProduct(product);
    }

    public void updateProduct(Long id, Product product) {
        product.setId(id);
        productService.updateProduct(product);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

}
