package services.impl;

import entities.Product;
import services.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para la implementación de ProductService")
class ProductServiceImplTest {

    @Mock
    private ShoppingCart cartMock;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(cartMock);
    }

    @Test
    @DisplayName("Prueba para agregar un nuevo producto al carrito")
    void testAddProductToCart() {
        // GIVEN
        Product product = new Product("producto ejemplo", 100.00);
        // Mockeamos el comportamiento del carrito
        doNothing().when(cartMock).addProduct(product);
        // Simulamos que el carrito tendrá 3 productos después de agregar
        when(cartMock.getTotalProducts()).thenReturn(3);

        // WHEN
        productService.addProductToCart(product, 3);

        // THEN
        assertEquals(3, cartMock.getTotalProducts());
        // Verificamos que se ejecute la cantidad de veces necesaria
        verify(cartMock, times(3)).addProduct(product);
    }

    @Test
    @DisplayName("Prueba para verificar que se arroja una excepción cuando el nombre del producto es nulo")
    void testAddProductToCartWhenNameIsNull() {
        // GIVEN
        Product product = new Product(null, 100.00);

        // WHEN & THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProductToCart(product, 3);
        });

        assertEquals("El nombre no debe ser nulo", exception.getMessage());
        assertEquals(0, cartMock.getTotalProducts());
        verify(cartMock, times(0)).addProduct(product);
    }

    @Test
    @DisplayName("Prueba para obtener la cantidad total de productos en el carrito")
    void testGetTotalProductsFromCart() {
        // GIVEN
        int actualTotalProducts = 3;
        when(cartMock.getTotalProducts()).thenReturn(actualTotalProducts);

        // WHEN
        int expectedTotalProducts = productService.getTotalProductsFromCart();

        // THEN
        assertEquals(expectedTotalProducts, actualTotalProducts);
        // Verificamos que se ejecute la cantidad de veces necesaria
        verify(cartMock, times(1)).getTotalProducts();
    }
    
}
