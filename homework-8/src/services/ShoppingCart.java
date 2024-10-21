package services;

import entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        // ACCEDE A BD = INSERT INTO
        products.add(product);
    }

    public int getTotalProducts() {
        // ACCEDE A BD = SELECT COUNT(*)
        return products.size();
    }

    public List<Product> getProducts() {
        // ACCEDE A BD = SELECT *
        return products;
    }

}
