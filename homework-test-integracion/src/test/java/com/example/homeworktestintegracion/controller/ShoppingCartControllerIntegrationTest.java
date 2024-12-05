package com.example.homeworktestintegracion.controller;

import com.example.homeworktestintegracion.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    public void testAddProductToCart() throws Exception {
        // Given
        Long cartId = 1L;
        Long productId = 1L;

        // When + Then
        mockMvc.perform(post("/api/cart/{cartId}/add/{productId}", cartId, productId))
            .andExpect(status().isOk());

        verify(productService, times(1)).addProductToCart(cartId, productId);
    }

}
