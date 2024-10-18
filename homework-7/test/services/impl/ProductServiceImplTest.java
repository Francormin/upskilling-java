package services.impl;

import entities.Product;
import services.ShoppingCart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

@DisplayName("Pruebas unitarias para la implementación de ProductService")
class ProductServiceImplTest {

    private ShoppingCart cart;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        productService = new ProductServiceImpl(cart);
    }

    @Test
    @DisplayName("Verifica que se lance una excepción al agregar un producto con nombre nulo")
    void testAddProductToCartWithNullName() {
        // GIVEN
        Product invalidProduct = new Product(null, 2.99);

        // WHEN / THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.addProductToCart(invalidProduct, 1);
        });
    }

    @Test
    @DisplayName("Verifica que no se agreguen productos con cantidad cero o negativa")
    void testAddProductWithZeroOrNegativeQuantity() {
        // GIVEN
        Product product = new Product("book", 15.50);

        // WHEN
        productService.addProductToCart(product, 0);
        productService.addProductToCart(product, -5);

        // THEN
        Assertions.assertEquals(0, cart.getTotalProducts());
    }

    @Test
    @DisplayName("Verifica que los productos se agreguen correctamente al carrito")
    void testAddProductToCart() {
        // GIVEN
        Product productToAdd = new Product("water bottle", 3.99);
        int quantityToAdd = 3;

        // WHEN
        productService.addProductToCart(productToAdd, quantityToAdd);

        // THEN
        Assertions.assertEquals(quantityToAdd, cart.getTotalProducts());
        Assertions.assertEquals(productToAdd, cart.getProducts().get(0));
    }

    @Test
    @DisplayName("Verifica que el total de productos en el carrito sea correcto")
    void testGetTotalProductsFromCart() {
        // GIVEN
        Product product1 = new Product("milk", 2.99);
        Product product2 = new Product("bread", 1.50);
        productService.addProductToCart(product1, 1);
        productService.addProductToCart(product2, 2);

        // WHEN
        int totalProductsInCart = productService.getTotalProductsFromCart();

        // THEN
        Assertions.assertEquals(3, totalProductsInCart);
    }

}
