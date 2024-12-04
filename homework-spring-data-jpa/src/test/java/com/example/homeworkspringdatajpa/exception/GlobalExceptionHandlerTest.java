package com.example.homeworkspringdatajpa.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleBookNotFoundException() {
        // Given
        BookNotFoundException ex = new BookNotFoundException("Book not found");

        // When
        ResponseEntity<String> response = exceptionHandler.handleBookNotFoundException(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody());
    }

    @Test
    void shouldHandleInvalidBookDataException() {
        // Given
        InvalidBookDataException ex = new InvalidBookDataException("Invalid book data");

        // When
        ResponseEntity<String> response = exceptionHandler.handleInvalidBookDataException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid book data", response.getBody());
    }

    @Test
    void shouldHandleInvalidArgumentException() {
        // Given
        IllegalArgumentException ex = new IllegalArgumentException("Title must not be empty.");

        // When
        ResponseEntity<String> response = exceptionHandler.handleInvalidArgumentException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Title must not be empty.", response.getBody());
    }

    @Test
    void shouldHandleValidationExceptions() {
        // Given
        BindingResult bindingResultMock = mock(BindingResult.class);

        FieldError fieldError1 = new FieldError(
            "bookDTO",
            "title",
            "Title cannot be null nor blank"
        );

        FieldError fieldError2 = new FieldError(
            "bookDTO",
            "publishYear",
            "Publish year must be equal or greater than 1600"
        );

        when(bindingResultMock.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResultMock);

        // When
        ResponseEntity<List<String>> response = exceptionHandler.handleValidationExceptions(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains("title: Title cannot be null nor blank"));
        assertTrue(response.getBody().contains("publishYear: Publish year must be equal or greater than 1600"));
    }

}
