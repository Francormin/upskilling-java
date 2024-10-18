package services;

import entities.Product;

public interface ProductService {

    void addProductToCart(Product product, int quantityToAdd);

    int getTotalProductsFromCart();

}
