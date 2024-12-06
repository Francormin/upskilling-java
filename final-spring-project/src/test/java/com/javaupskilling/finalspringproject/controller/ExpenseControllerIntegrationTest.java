package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.model.User;
import com.javaupskilling.finalspringproject.repository.ExpenseCategoryRepository;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.repository.UserRepository;
import com.javaupskilling.finalspringproject.service.impl.ExpenseServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ExpenseControllerIntegrationTest {

    @Autowired
    private ExpenseServiceImpl expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    private ExpenseRequestDto expenseRequestDto;
    private ExpenseCategory expenseCategory;
    private User user;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();

        expenseCategory = new ExpenseCategory();
        expenseCategory.setName("Food");
        expenseCategory = expenseCategoryRepository.save(expenseCategory);

        user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user = userRepository.save(user);

        expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setAmount(150.0);
        expenseRequestDto.setDate("05-12-2024");
        expenseRequestDto.setDescription("Dinner with friends");
        expenseRequestDto.setExpenseCategoryId(expenseCategory.getId());
        expenseRequestDto.setUserId(user.getId());
    }

    @Test
    void testGetAllIntegration() {
        // Given
        expenseService.create(expenseRequestDto);

        // When
        List<ExpenseResponseDto> responseDtos = expenseService.getAll();

        // Then
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals(150.0, responseDtos.get(0).getAmount());
        assertEquals("Dinner with friends", responseDtos.get(0).getDescription());
        assertEquals("05-12-2024", responseDtos.get(0).getDate());
        assertEquals(expenseCategory.getName(), responseDtos.get(0).getExpenseCategoryName());
        assertEquals(user.getEmail(), responseDtos.get(0).getUserEmail());
    }

    @Test
    void testGetByIdIntegration() {
        // Given
        ExpenseResponseDto createdExpense = expenseService.create(expenseRequestDto);
        Expense savedExpense = expenseRepository.findAll().get(0);
        Long createdExpenseId = savedExpense.getId();

        // When
        ExpenseResponseDto responseDto = expenseService.getById(createdExpenseId);

        // Then
        assertNotNull(responseDto);
        assertEquals(createdExpense.getAmount(), responseDto.getAmount());
        assertEquals(createdExpense.getDate(), responseDto.getDate());
        assertEquals(createdExpense.getDescription(), responseDto.getDescription());
        assertEquals(createdExpense.getExpenseCategoryName(), responseDto.getExpenseCategoryName());
        assertEquals(createdExpense.getUserEmail(), responseDto.getUserEmail());
    }

    @Test
    void testGetByUserIdIntegration() {
        // Given
        expenseService.create(expenseRequestDto);

        // When
        List<ExpenseResponseDto> responseDtos = expenseService.getByUserId(user.getId());

        // Then
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals("john.doe@example.com", responseDtos.get(0).getUserEmail());
    }

    @Test
    void testGetByDateIntegration() {
        // Given
        expenseService.create(expenseRequestDto);

        // When
        List<ExpenseResponseDto> responseDtos = expenseService.getByDate("05-12-2024");

        // Then
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals("Dinner with friends", responseDtos.get(0).getDescription());
    }

    @Test
    void testCreateExpenseIntegration() {
        // When
        ExpenseResponseDto responseDto = expenseService.create(expenseRequestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals(expenseRequestDto.getAmount(), responseDto.getAmount());
        assertEquals(expenseRequestDto.getDescription(), responseDto.getDescription());
        assertEquals("Food", responseDto.getExpenseCategoryName());
        assertEquals("john.doe@example.com", responseDto.getUserEmail());
    }

    @Test
    void testUpdateExpenseIntegration() {
        // Given
        ExpenseResponseDto createdExpense = expenseService.create(expenseRequestDto);
        Expense savedExpense = expenseRepository.findAll().get(0);
        Long createdExpenseId = savedExpense.getId();

        ExpenseRequestDto updatedRequestDto = new ExpenseRequestDto();
        updatedRequestDto.setAmount(200.0);
        updatedRequestDto.setDate("06-12-2024");
        updatedRequestDto.setDescription("Updated Dinner");
        updatedRequestDto.setExpenseCategoryId(expenseCategory.getId());
        updatedRequestDto.setUserId(user.getId());

        // When
        ExpenseResponseDto updatedExpense = expenseService.update(createdExpenseId, updatedRequestDto);

        // Then
        assertNotNull(updatedExpense);
        assertEquals(200.0, updatedExpense.getAmount());
        assertEquals("Updated Dinner", updatedExpense.getDescription());
        assertEquals("06-12-2024", updatedExpense.getDate());
        assertEquals(expenseCategory.getName(), updatedExpense.getExpenseCategoryName());
        assertEquals(user.getEmail(), updatedExpense.getUserEmail());
    }

    @Test
    void testDeleteExpenseIntegration() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(150.0);
        expense.setDate("05-12-2024");
        expense.setDescription("Dinner");
        expense.setExpenseCategory(expenseCategory);
        expense.setUser(user);
        expense = expenseRepository.save(expense);

        // When
        expenseService.delete(expense.getId());

        // Then
        assertEquals(0, expenseRepository.count());
        assertTrue(expenseRepository.findById(expense.getId()).isEmpty());
    }

}
