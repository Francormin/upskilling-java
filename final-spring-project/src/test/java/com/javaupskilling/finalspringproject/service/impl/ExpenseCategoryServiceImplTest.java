package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.repository.ExpenseCategoryRepository;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryServiceImplTest {

    @Mock
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseCategoryServiceImpl expenseCategoryService;

    private ExpenseCategory expenseCategory;

    @BeforeEach
    void setUp() {
        expenseCategory = new ExpenseCategory();
        expenseCategory.setId(1L);
        expenseCategory.setName("Vacations");
        expenseCategory.setDescription("Food, transportation, hotels and tour trips payments");
        expenseCategory.setExpenses(new ArrayList<>());
    }

    @Test
    void getAll_Success() {
        // Given
        when(expenseCategoryRepository.findAll()).thenReturn(List.of(expenseCategory));

        // When
        List<ExpenseCategoryResponseDto> result = expenseCategoryService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Vacations", result.get(0).getName());
        verify(expenseCategoryRepository, times(1)).findAll();
    }

    @Test
    void getAll_ExpenseCategoriesNotFound() {
        // Given
        when(expenseCategoryRepository.findAll()).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.getAll());

        assertEquals(
            "ExpenseCategory: No expense categories found in the system",
            exception.getMessage()
        );

        verify(expenseCategoryRepository, times(1)).findAll();
    }

    @Test
    void getById_Success() {
        // Given
        Long id = 1L;
        when(expenseCategoryRepository.findById(id)).thenReturn(Optional.of(expenseCategory));

        // When
        ExpenseCategoryResponseDto result = expenseCategoryService.getById(id);

        // Then
        assertNotNull(result);
        assertEquals("Food, transportation, hotels and tour trips payments", result.getDescription());
        assertEquals(0, result.getExpenses().size());
        verify(expenseCategoryRepository, times(1)).findById(id);
    }

    @Test
    void getById_ExpenseCategoryNotFound() {
        // Given
        when(expenseCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.getById(1L));
        assertEquals("ExpenseCategory with ID 1 not found", exception.getMessage());
        verify(expenseCategoryRepository, times(1)).findById(anyLong());
    }

}
