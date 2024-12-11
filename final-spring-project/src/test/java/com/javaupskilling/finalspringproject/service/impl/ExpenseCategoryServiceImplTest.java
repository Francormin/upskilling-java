package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
    private ExpenseCategoryRequestDto expenseCategoryRequestDto;

    @BeforeEach
    void setUp() {
        expenseCategory = new ExpenseCategory();
        expenseCategory.setId(1L);
        expenseCategory.setName("Vacations");
        expenseCategory.setDescription("Food, transportation, hotels and tour trips payments");
        expenseCategory.setExpenses(new ArrayList<>());
    }

    @Test
    void getAll_ShouldReturnListOfExpenseCategories_WhenExpenseCategoriesExist() {
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
    void getAll_ShouldThrowException_WhenNoExpenseCategoriesExist() {
        // Given
        when(expenseCategoryRepository.findAll()).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.getAll()
        );

        assertEquals(
            "ExpenseCategory: No expense categories found in the system",
            exception.getMessage()
        );

        verify(expenseCategoryRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnExpenseCategory_WhenExpenseCategoryExistsWithId() {
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
    void getById_ShouldThrowException_WhenExpenseCategoryDoesNotExistWithId() {
        // Given
        when(expenseCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.getById(1L)
        );

        assertEquals("ExpenseCategory with ID 1 not found", exception.getMessage());
        verify(expenseCategoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByName_ShouldReturnListOfExpenseCategories_WhenExpenseCategoriesExistWithName() {
        // Given
        String name = "vacation";
        when(expenseCategoryRepository.findByNameContainingIgnoreCase(name))
            .thenReturn(List.of(expenseCategory));

        // When
        List<ExpenseCategoryResponseDto> result = expenseCategoryService.getByName(name);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Vacations", result.get(0).getName());
        verify(expenseCategoryRepository, times(1))
            .findByNameContainingIgnoreCase(name);
    }

    @Test
    void getByName_ShouldThrowException_WhenNoExpenseCategoriesExistWithName() {
        // Given
        when(expenseCategoryRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.getByName("groceries")
        );

        assertEquals(
            "ExpenseCategory: No expense categories with name containing 'groceries' in the system",
            exception.getMessage()
        );

        verify(expenseCategoryRepository, times(1))
            .findByNameContainingIgnoreCase(anyString());
    }

    @Test
    void create_ShouldSaveAndReturnExpenseCategory() {
        // Given
        expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("New Vacations");
        expenseCategoryRequestDto.setDescription("New travel-related expenses");

        when(expenseCategoryRepository.save(any(ExpenseCategory.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ExpenseCategoryResponseDto result = expenseCategoryService.create(expenseCategoryRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("New Vacations", result.getName());
        assertEquals(0, result.getExpenses().size());
        verify(expenseCategoryRepository, times(1)).save(any(ExpenseCategory.class));
    }

    @Test
    void update_ShouldUpdateAndReturnExpenseCategory_WhenExpenseCategoryExists() {
        // Given
        expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("Updated Vacations");
        expenseCategoryRequestDto.setDescription("Updated travel-related expenses");

        when(expenseCategoryRepository.findById(1L)).thenReturn(Optional.of(expenseCategory));
        when(expenseCategoryRepository.save(any(ExpenseCategory.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ExpenseCategoryResponseDto result = expenseCategoryService.update(1L, expenseCategoryRequestDto);

        // Then
        assertEquals("Updated Vacations", result.getName());
        assertEquals("Updated travel-related expenses", result.getDescription());
        verify(expenseCategoryRepository, times(1)).findById(1L);
        verify(expenseCategoryRepository, times(1)).save(any(ExpenseCategory.class));
    }

    @Test
    void update_ShouldThrowException_WhenExpenseCategoryDoesNotExist() {
        // Given
        Long nonExistingId = 99L;
        expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("Updated Vacations");
        expenseCategoryRequestDto.setDescription("Updated travel-related expenses");

        when(expenseCategoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.update(nonExistingId, expenseCategoryRequestDto)
        );

        assertEquals("ExpenseCategory with ID 99 not found", exception.getMessage());
        verify(expenseCategoryRepository, times(1)).findById(nonExistingId);
        verify(expenseCategoryRepository, times(0)).save(any(ExpenseCategory.class));
    }

    @Test
    void delete_ShouldDeleteExpenseCategory_WhenExpenseCategoryExists() {
        // Given
        when(expenseCategoryRepository.existsById(anyLong())).thenReturn(true);
        when(expenseRepository.findByExpenseCategoryId(anyLong())).thenReturn(List.of(new Expense()));
        doNothing().when(expenseCategoryRepository).deleteById(anyLong());

        // When
        expenseCategoryService.delete(anyLong());

        // Then
        verify(expenseCategoryRepository, times(1)).existsById(anyLong());
        verify(expenseRepository, times(1)).findByExpenseCategoryId(anyLong());
        verify(expenseRepository, times(1)).deleteAll(anyList());
        verify(expenseCategoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void delete_ShouldThrowException_WhenExpenseCategoryDoesNotExist() {
        // Given
        when(expenseCategoryRepository.existsById(99L)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseCategoryService.delete(99L)
        );

        assertEquals("ExpenseCategory with ID 99 not found", exception.getMessage());
        verify(expenseCategoryRepository, times(1)).existsById(99L);
        verify(expenseRepository, times(0)).findByExpenseCategoryId(99L);
    }

}
