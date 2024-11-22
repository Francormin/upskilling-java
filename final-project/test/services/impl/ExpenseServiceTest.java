package services.impl;

import dao.DAO;
import entities.Expense;
import entities.ExpenseCategory;
import exceptions.InvalidExpenseAmountException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {
    @Mock
    private DAO<Expense> dao;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;

    @BeforeEach
    void setUp() {
        ExpenseCategory category = new ExpenseCategory("Food");
        try {
            expense = new Expense(1, 100.0, "22/11/2024", category, "Lunch");
        } catch (InvalidExpenseAmountException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Prueba del método 'getAll' para obtener todos los gastos guardados en la base de datos")
    void getAll() {
        // Given
        List<Expense> expectedExpenses = Collections.singletonList(expense);
        when(dao.getAll()).thenReturn(expectedExpenses);

        // When
        List<Expense> actualExpenses = expenseService.getAll();

        // Then
        assertEquals(expectedExpenses, actualExpenses);
        verify(dao, times(1)).getAll();
    }

    @Test
    @DisplayName("Prueba del método 'getById' para obtener un gasto por su id")
    void getById() {
        // Given
        int id = 1;
        when(dao.getById(id)).thenReturn(expense);

        // When
        Expense actualExpense = expenseService.getById(id);

        // Then
        assertEquals(expense, actualExpense);
        verify(dao, times(1)).getById(id);
    }

    @Test
    @DisplayName("Prueba del método 'add' para crear un gasto y guardarlo en la base de datos")
    void add() {
        // Given
        doNothing().when(dao).add(expense);

        // When
        expenseService.add(expense);

        // Then
        verify(dao, times(1)).add(expense);
    }

    @Test
    @DisplayName("Prueba del método 'update' para actualizar un gasto por su id")
    void update() {
        // Given
        int id = 1;
        doNothing().when(dao).update(id, expense);

        // When
        expenseService.update(id, expense);

        // Then
        verify(dao, times(1)).update(id, expense);
    }

    @Test
    @DisplayName("Prueba del método 'delete' para eliminar un gasto por su id")
    void delete() {
        // Given
        int id = 1;
        doNothing().when(dao).delete(id);

        // When
        expenseService.delete(id);

        // Then
        verify(dao, times(1)).delete(id);
    }
}