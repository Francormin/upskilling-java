package services.impl;

import entities.Product;
import services.ProductService;
import services.ShoppingCart;

import java.util.Objects;
import java.util.stream.IntStream;

public class ProductServiceImpl implements ProductService {

    private final ShoppingCart cart;

    public ProductServiceImpl(ShoppingCart shoppingCart) {
        this.cart = shoppingCart;
    }

    @Override
    public void addProductToCart(Product product, int quantityToAdd) {
        if (Objects.isNull(product.getName())) {
            throw new IllegalArgumentException("El nombre no debe ser nulo");
        }

        IntStream.range(0, quantityToAdd).forEach(i -> cart.addProduct(product));
    }

    @Override
    public int getTotalProductsFromCart() {
        return cart.getTotalProducts();
    }

}
