package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.repository.IRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseServiceTest {

    private ExpenseService expenseService;
    private IRepository<Expense> expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository = Mockito.mock(IRepository.class);
        expenseService = new ExpenseService(expenseRepository);
    }

    @Test
    void getAll() {
        // Given
        Expense expense1 = new Expense(
            100.0,
            "2024-12-01",
            new ExpenseCategory("Food", "Meals"),
            "Lunch"
        );

        Expense expense2 = new Expense(
            50.0,
            "2024-12-02",
            new ExpenseCategory("Transport", "Daily commute"),
            "Bus fare"
        );

        when(expenseRepository.getAll()).thenReturn(Arrays.asList(expense1, expense2));

        // When
        List<Expense> expenses = expenseService.getAll();

        // Then
        assertNotNull(expenses);
        assertEquals(2, expenses.size());
        assertEquals("food", expenses.get(0).getExpenseCategory().getName());
        verify(expenseRepository, times(1)).getAll();
    }

    @Test
    void getById() throws ExpenseNotFoundException {
        // Given
        Long expenseId = 1L;
        Expense expense = new Expense(
            200.0,
            "2024-12-01",
            new ExpenseCategory("Rent", "Monthly housing"),
            "Apartment rent"
        );
        expense.setId(expenseId);

        when(expenseRepository.getById(expenseId)).thenReturn(Optional.of(expense));

        // When
        Expense foundExpense = expenseService.getById(expenseId);

        // Then
        assertNotNull(foundExpense);
        assertEquals(expenseId, foundExpense.getId());
        assertEquals("rent", foundExpense.getExpenseCategory().getName());
        verify(expenseRepository, times(1)).getById(expenseId);
    }

    @Test
    void getByIdThrowsExceptionWhenNotFound() {
        // Given
        Long expenseId = 99L;
        when(expenseRepository.getById(expenseId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.getById(expenseId));
        verify(expenseRepository, times(1)).getById(expenseId);
    }

    @Test
    void create() {
        // Given
        Expense expense = new Expense(
            300.0,
            "2024-12-01",
            new ExpenseCategory("Utilities", "Electricity bills"),
            "Electricity"
        );

        doNothing().when(expenseRepository).create(expense);

        // When
        expenseService.create(expense);

        // Then
        verify(expenseRepository, times(1)).create(expense);
    }

    @Test
    void createThrowsRuntimeException() {
        // Given
        Expense expense = new Expense(
            300.0,
            "2024-12-01",
            new ExpenseCategory("Utilities", "Electricity bills"),
            "Electricity"
        );

        doThrow(new RuntimeException("Database error")).when(expenseRepository).create(expense);

        // When / Then
        assertThrows(RuntimeException.class, () -> expenseService.create(expense));
        verify(expenseRepository, times(1)).create(expense);
    }

    @Test
    void update() throws ExpenseNotFoundException {
        // Given
        Long expenseId = 1L;
        Expense expense = new Expense(
            400.0,
            "2024-12-01",
            new ExpenseCategory("Health", "Medical expenses"),
            "Doctor visit"
        );
        expense.setId(expenseId);

        when(expenseRepository.getById(expenseId)).thenReturn(Optional.of(expense));
        doNothing().when(expenseRepository).update(expenseId, expense);

        // When
        expenseService.update(expenseId, expense);

        // Then
        verify(expenseRepository, times(1)).getById(expenseId);
        verify(expenseRepository, times(1)).update(expenseId, expense);
    }

    @Test
    void updateThrowsRuntimeException() {
        // Given
        Long expenseId = 1L;
        Expense expense = new Expense(
            400.0,
            "2024-12-01",
            new ExpenseCategory("Health", "Medical expenses"),
            "Doctor visit"
        );
        expense.setId(expenseId);

        when(expenseRepository.getById(expenseId)).thenReturn(Optional.of(expense));
        doThrow(new RuntimeException("Database error")).when(expenseRepository).update(expenseId, expense);

        // When / Then
        assertThrows(RuntimeException.class, () -> expenseService.update(expenseId, expense));
        verify(expenseRepository, times(1)).getById(expenseId);
        verify(expenseRepository, times(1)).update(expenseId, expense);
    }

    @Test
    void delete() throws ExpenseNotFoundException {
        // Given
        Long expenseId = 1L;
        Expense expense = new Expense(
            200.0,
            "2024-12-01",
            new ExpenseCategory("Rent", "Monthly housing"),
            "Apartment rent"
        );
        expense.setId(expenseId);

        when(expenseRepository.getById(expenseId)).thenReturn(Optional.of(expense));
        doNothing().when(expenseRepository).delete(expenseId);

        // When
        expenseService.delete(expenseId);

        // Then
        verify(expenseRepository, times(1)).getById(expenseId);
        verify(expenseRepository, times(1)).delete(expenseId);
    }

    @Test
    void deleteThrowsExpenseNotFoundException() {
        // Given
        Long expenseId = 99L;
        when(expenseRepository.getById(expenseId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.delete(expenseId));
        verify(expenseRepository, times(1)).getById(expenseId);
    }

}
