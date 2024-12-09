package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.ExpenseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExpenseService expenseService;

    @Test
    @DisplayName("GET /api/v1/expenses - Should return list of expenses")
    void getAll_ShouldReturnListOfExpenses() throws Exception {
        // Given
        ExpenseResponseDto expense = new ExpenseResponseDto();
        expense.setAmount(100.0);
        expense.setDate("01-01-2024");
        expense.setDescription("Test Expense");
        expense.setExpenseCategoryName("Food");
        expense.setUserEmail("user@example.com");

        List<ExpenseResponseDto> expenses = List.of(expense);

        when(expenseService.getAll()).thenReturn(expenses);

        // When & Then
        mockMvc.perform(get("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].amount", is(100.0)))
            .andExpect(jsonPath("$[0].date", is("01-01-2024")))
            .andExpect(jsonPath("$[0].description", is("Test Expense")))
            .andExpect(jsonPath("$[0].expenseCategoryName", is("Food")))
            .andExpect(jsonPath("$[0].userEmail", is("user@example.com")));

        verify(expenseService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/v1/expenses - Should return not found when getting non existing expenses")
    void getAll_shouldReturnNotFoundWhenGettingNonExistingExpenses() throws Exception {
        // Given
        Long nonExistingId = 999L;
        given(expenseService.getById(nonExistingId))
            .willThrow(new EntityNotFoundException("Expense", nonExistingId));

        // When & Then
        mockMvc.perform(get("/api/v1/expenses/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(result -> Objects.equals(
                result.getResponse().getErrorMessage(),
                "\"Expense with ID 999 not found\"")
            );

        verify(expenseService, times(1)).getById(999L);
    }

    @Test
    @DisplayName("GET /api/v1/expenses/{id} - Should return an expense by ID")
    void getById_ShouldReturnExpense() throws Exception {
        // Given
        ExpenseResponseDto expense = new ExpenseResponseDto();
        expense.setAmount(100.0);
        expense.setDate("01-01-2024");
        expense.setDescription("Test Expense");
        expense.setExpenseCategoryName("Food");
        expense.setUserEmail("user@example.com");

        when(expenseService.getById(anyLong())).thenReturn(expense);

        // When & Then
        mockMvc.perform(get("/api/v1/expenses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.amount", is(100.0)))
            .andExpect(jsonPath("$.date", is("01-01-2024")))
            .andExpect(jsonPath("$.description", is("Test Expense")))
            .andExpect(jsonPath("$.expenseCategoryName", is("Food")))
            .andExpect(jsonPath("$.userEmail", is("user@example.com")));

        verify(expenseService, times(1)).getById(anyLong());
    }

    @Test
    @DisplayName("GET /api/v1/expenses/{id} - Should return not found when getting non existing expense")
    void getById_shouldReturnNotFoundWhenGettingNonExistingExpense() throws Exception {
        // Given
        long nonExistingId = 999L;
        given(expenseService.getById(nonExistingId))
            .willThrow(new EntityNotFoundException("Expense", nonExistingId));

        // When & Then
        mockMvc.perform(get("/api/v1/expenses/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", is("Expense with ID 999 not found")))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/v1/expenses - Should create a new expense")
    void create_ShouldReturnCreatedExpense() throws Exception {
        // Given
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setAmount(200.0);
        request.setDate("02-01-2024");
        request.setDescription("New Expense");
        request.setExpenseCategoryId(1L);
        request.setUserId(1L);

        ExpenseResponseDto response = new ExpenseResponseDto();
        response.setAmount(200.0);
        response.setDate("02-01-2024");
        response.setDescription("New Expense");
        response.setExpenseCategoryName("Utilities");
        response.setUserEmail("user@example.com");

        when(expenseService.create(any(ExpenseRequestDto.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.amount", is(200.0)))
            .andExpect(jsonPath("$.date", is("02-01-2024")))
            .andExpect(jsonPath("$.description", is("New Expense")))
            .andExpect(jsonPath("$.expenseCategoryName", is("Utilities")))
            .andExpect(jsonPath("$.userEmail", is("user@example.com")));

        verify(expenseService, times(1)).create(any(ExpenseRequestDto.class));
    }

    @Test
    @DisplayName("POST /api/v1/expenses - Should return bad request when creating expense with invalid data")
    void create_shouldReturnBadRequestWhenCreatingExpenseWithInvalidData() throws Exception {
        // Given
        ExpenseRequestDto invalidRequest = new ExpenseRequestDto();
        invalidRequest.setAmount(-10.0);
        invalidRequest.setDate("invalid-date");

        // When & Then
        mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasSize(5)))
            .andExpect(jsonPath("$.errors", containsInAnyOrder(
                containsString("Amount must be greater than zero"),
                containsString("Date can only contain numbers spaces, and hyphens"),
                containsString("Date must have 10 characters format: dd-MM-yyyy"),
                containsString("ExpenseCategoryId cannot be null"),
                containsString("UserId cannot be null")))
            );

        verify(expenseService, times(0)).create(invalidRequest);
    }

    @Test
    @DisplayName("PUT /api/v1/expenses/{id} - Should update an existing expense")
    void update_ShouldReturnUpdatedExpense() throws Exception {
        // Given
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setAmount(300.0);
        request.setDate("03-01-2024");
        request.setDescription("Updated Expense");
        request.setExpenseCategoryId(2L);
        request.setUserId(2L);

        ExpenseResponseDto response = new ExpenseResponseDto();
        response.setAmount(300.0);
        response.setDate("03-01-2024");
        response.setDescription("Updated Expense");
        response.setExpenseCategoryName("Travel");
        response.setUserEmail("updated.user@example.com");

        when(expenseService.update(anyLong(), any(ExpenseRequestDto.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/v1/expenses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.amount", is(300.0)))
            .andExpect(jsonPath("$.date", is("03-01-2024")))
            .andExpect(jsonPath("$.description", is("Updated Expense")))
            .andExpect(jsonPath("$.expenseCategoryName", is("Travel")))
            .andExpect(jsonPath("$.userEmail", is("updated.user@example.com")));

        verify(expenseService, times(1)).update(anyLong(), any(ExpenseRequestDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/expenses/{id} - Should return not found when updating non existing expense")
    void update_shouldReturnNotFoundWhenUpdatingNonExistingExpense() throws Exception {
        // Given
        Long nonExistingId = 999L;
        ExpenseRequestDto validRequest = new ExpenseRequestDto();
        validRequest.setAmount(100.0);
        validRequest.setDate("10-10-2024");
        validRequest.setDescription("Description");
        validRequest.setExpenseCategoryId(1L);
        validRequest.setUserId(1L);

        given(expenseService.update(nonExistingId, validRequest))
            .willThrow(new EntityNotFoundException("Expense", nonExistingId));

        // When & Then
        mockMvc.perform(put("/api/v1/expenses/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Expense with ID 999 not found")));

        verify(expenseService, times(1)).update(nonExistingId, validRequest);
    }

    @Test
    @DisplayName("DELETE /api/v1/expenses/{id} - Should delete an expense")
    void delete_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(expenseService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/v1/expenses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Expense deleted successfully"));

        verify(expenseService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("DELETE /api/v1/expenses/{id} - Should return not found when deleting non existing expense")
    void delete_shouldReturnNotFoundWhenDeletingNonExistingExpense() throws Exception {
        // Given
        Long nonExistingId = 999L;
        doThrow(new EntityNotFoundException("Expense", nonExistingId))
            .when(expenseService).delete(nonExistingId);

        // When & Then
        mockMvc.perform(delete("/api/v1/expenses/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Expense with ID 999 not found")));

        verify(expenseService, times(1)).delete(nonExistingId);
    }

}
