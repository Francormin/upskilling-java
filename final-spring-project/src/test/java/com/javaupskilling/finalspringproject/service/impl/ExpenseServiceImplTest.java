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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
        expenseRequestDto.setDate("05-12-2024");
        expenseRequestDto.setDescription("Lunch");
        expenseRequestDto.setExpenseCategoryId(1L);
        expenseRequestDto.setUserId(1L);
        return expenseRequestDto;
    }

    private Expense createExpense() {
        Expense expense = new Expense();
        expense.setAmount(100.0);
        expense.setDate("05-12-2024");
        expense.setDescription("Lunch");
        expense.setExpenseCategory(expenseCategory);
        expense.setUser(user);
        return expense;
    }

    @Test
    void getAll_ShouldReturnListOfExpenses_WhenExpensesExist() {
        // Given
        Expense expense = createExpense();
        when(expenseRepository.findAll()).thenReturn(List.of(expense));

        // When
        List<ExpenseResponseDto> result = expenseService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowException_WhenNoExpensesExist() {
        // Given
        when(expenseRepository.findAll()).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.getAll()
        );

        assertEquals("Expense: No expenses found in the system", exception.getMessage());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnExpense_WhenExpenseExistsWithId() {
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
    void getById_ShouldThrowException_WhenExpenseDoesNotExistWithId() {
        // Given
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.getById(1L)
        );

        assertEquals("Expense with ID 1 not found", exception.getMessage());
        verify(expenseRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByUserId_ShouldReturnListOfExpenses_WhenExpensesExistWithUserId() {
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
    void getByUserId_ShouldThrowException_WhenNoExpensesExistWithUserId() {
        // Given
        when(expenseRepository.findByUserId(anyLong())).thenReturn(List.of());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.getByUserId(1L)
        );

        assertEquals("Expense: No expenses found for user ID 1", exception.getMessage());
        verify(expenseRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    void create_ShouldSaveAndReturnExpense_WhenExpenseCategoryAndUserExist() {
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
    void create_ShouldThrowException_WhenExpenseCategoryDoesNotExist() {
        // Given
        when(expenseCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.create(expenseRequestDto)
        );

        assertEquals("ExpenseCategory with ID 1 not found", exception.getMessage());
        verify(expenseCategoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete_ShouldDeleteExpense_WhenExpenseExists() {
        // Given
        when(expenseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(expenseRepository).deleteById(1L);

        // When
        expenseService.delete(1L);

        // Then
        verify(expenseRepository, times(1)).existsById(1L);
        verify(expenseRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenExpenseDoesNotExist() {
        // Given
        when(expenseRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> expenseService.delete(1L)
        );

        assertEquals("Expense with ID 1 not found", exception.getMessage());
        verify(expenseRepository, times(1)).existsById(anyLong());
    }

}
