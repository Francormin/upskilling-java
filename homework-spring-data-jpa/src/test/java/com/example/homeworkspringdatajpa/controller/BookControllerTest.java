package com.example.homeworkspringdatajpa.controller;

import com.example.homeworkspringdatajpa.service.BookService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "",
                    "author": "Valid Author",
                    "publishYear": 2020,
                    "pageQuantity": 200
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]").value("title: Title cannot be null nor blank"));
    }

    @Test
    void shouldReturnBadRequestWhenPublishYearIsInvalid() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "Valid Title",
                    "author": "Valid Author",
                    "publishYear": 1500,
                    "pageQuantity": 200
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]")
                .value("publishYear: Publish year must be equal or greater than 1600"));
    }

    @Test
    void shouldReturnBadRequestWhenPageQuantityIsInvalid() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "Valid Title",
                    "author": "Valid Author",
                    "publishYear": 2020,
                    "pageQuantity": 10
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]")
                .value("pageQuantity: Page quantity must be equal or greater than 15"));
    }

}
