package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.model.User;
import com.javaupskilling.finalspringproject.repository.ExpenseCategoryRepository;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private ExpenseRequestDto expenseRequestDto;
    private ExpenseCategory expenseCategory;
    private User user;

    @BeforeEach
    void setUp() {
        expenseCategory = createExpenseCategory();
        user = createUser();
        expenseRequestDto = createExpenseRequestDto();
    }

    private ExpenseCategory createExpenseCategory() {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setId(1L);
        expenseCategory.setName("Food");
        return expenseCategory;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        return user;
    }

    private ExpenseRequestDto createExpenseRequestDto() {
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setAmount(100.0);
        expenseRequestDto.setDate("2024-12-05");
        expenseRequestDto.setDescription("Lunch");
        expenseRequestDto.setExpenseCategoryId(1L);
        expenseRequestDto.setUserId(1L);
        return expenseRequestDto;
    }

    private Expense createExpense() {
        Expense expense = new Expense();
        expense.setAmount(100.0);
        expense.setDate("2024-12-05");
        expense.setDescription("Lunch");
        expense.setExpenseCategory(expenseCategory);
        expense.setUser(user);
        return expense;
    }

    @Test
    void testGetById_Success() {
        // Given
        Expense expense = createExpense();
        expense.setId(1L);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        // When
        ExpenseResponseDto responseDto = expenseService.getById(1L);

        // Then
        assertNotNull(responseDto);
        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_ExpenseNotFound() {
        // Given
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.getById(1L));
        assertEquals("Expense with ID 1 not found", exception.getMessage());
    }

    @Test
    void testGetByUserId_Success() {
        // Given
        Expense expense = createExpense();
        expense.setId(1L);
        List<Expense> expenses = List.of(expense);

        when(expenseRepository.findByUserId(1L)).thenReturn(expenses);

        // When
        List<ExpenseResponseDto> responseDtos = expenseService.getByUserId(1L);

        // Then
        assertEquals(1, responseDtos.size());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetByUserId_ExpenseNotFound() {
        // Given
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.getByUserId(1L));
        assertEquals("User: No expenses found for user ID 1", exception.getMessage());
    }

    @Test
    void testCreateExpense_Success() {
        // Given
        Expense expense = createExpense();

        when(expenseCategoryRepository.findById(1L)).thenReturn(Optional.of(expenseCategory));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // When
        ExpenseResponseDto responseDto = expenseService.create(expenseRequestDto);

        // Then
        verify(expenseCategoryRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(expenseRepository, times(1)).save(any(Expense.class));
        assertNotNull(responseDto);
    }

    @Test
    void testCreateExpense_ExpenseCategoryNotFound() {
        // Given
        when(expenseCategoryRepository.findById(expenseRequestDto.getExpenseCategoryId()))
            .thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.create(expenseRequestDto));
        assertEquals("ExpenseCategory with ID 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteExpense_Success() {
        // Given
        when(expenseRepository.existsById(1L)).thenReturn(true);

        // When
        expenseService.delete(1L);

        // Then
        verify(expenseRepository, times(1)).existsById(1L);
        verify(expenseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteExpense_ExpenseNotFound() {
        // Given
        when(expenseRepository.existsById(1L)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.delete(1L));
        assertEquals("Expense with ID 1 not found", exception.getMessage());
    }

}
