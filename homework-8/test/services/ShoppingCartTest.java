package services;

import entities.Product;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas unitarias para las operaciones del carrito de compras")
class ShoppingCartTest {

    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    @DisplayName("El carrito debe estar vacío al crearse")
    void testEmptyCartOnCreation() {
        // WHEN
        int totalProducts = shoppingCart.getTotalProducts();

        // THEN
        Assertions.assertEquals(0, totalProducts);
        Assertions.assertTrue(shoppingCart.getProducts().isEmpty());
    }

    @Test
    @DisplayName("Debe permitir agregar productos duplicados")
    void testAddDuplicateProducts() {
        // GIVEN
        Product product = new Product("apple", 0.99);

        // WHEN
        shoppingCart.addProduct(product);
        shoppingCart.addProduct(product);

        // THEN
        Assertions.assertEquals(2, shoppingCart.getTotalProducts());
        Assertions.assertEquals(product, shoppingCart.getProducts().get(0));
        Assertions.assertEquals(product, shoppingCart.getProducts().get(1));
    }

    @Test
    @DisplayName("Debe permitir agregar múltiples productos diferentes")
    void testAddMultipleDifferentProducts() {
        // GIVEN
        Product product1 = new Product("milk", 1.99);
        Product product2 = new Product("bread", 2.50);

        // WHEN
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        // THEN
        Assertions.assertEquals(2, shoppingCart.getTotalProducts());
        Assertions.assertTrue(shoppingCart.getProducts().contains(product1));
        Assertions.assertTrue(shoppingCart.getProducts().contains(product2));
    }

    @Test
    @DisplayName("Debe agregar un producto correctamente")
    void testAddProduct() {
        // GIVEN
        Product productToAdd = new Product("water bottle", 3.99);

        // WHEN
        shoppingCart.addProduct(productToAdd);
        int totalCartProducts = shoppingCart.getTotalProducts();

        // THEN
        int expectedTotalCartProducts = 1;
        Assertions.assertEquals(expectedTotalCartProducts, totalCartProducts);
    }

    @Test
    @DisplayName("Debe retornar el número total de productos en el carrito")
    void testGetTotalProducts() {
        // GIVEN
        Product product1 = new Product("water bottle", 3.99);
        Product product2 = new Product("book", 15.50);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        // WHEN
        int totalProducts = shoppingCart.getTotalProducts();

        // THEN
        int expectedTotalProducts = 2;
        Assertions.assertEquals(expectedTotalProducts, totalProducts);
    }

    @Test
    @DisplayName("Debe retornar la lista de productos en el carrito")
    void testGetProducts() {
        // GIVEN
        Product product1 = new Product("water bottle", 3.99);
        Product product2 = new Product("book", 15.50);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        // WHEN
        List<Product> productsInCart = shoppingCart.getProducts();

        // THEN
        Assertions.assertEquals(2, productsInCart.size());
        Assertions.assertTrue(productsInCart.contains(product1));
        Assertions.assertTrue(productsInCart.contains(product2));
    }

}
