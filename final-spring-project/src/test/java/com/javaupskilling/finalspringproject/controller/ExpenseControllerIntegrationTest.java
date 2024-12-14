package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.ExpenseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
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

    private static final String BASE_URL = "/api/v1/expenses";

    private ExpenseResponseDto createTestExpenseResponse() {
        ExpenseResponseDto expenseResponse = new ExpenseResponseDto();
        expenseResponse.setAmount(100.0);
        expenseResponse.setDate("01-01-2024");
        expenseResponse.setDescription("Test Expense");
        expenseResponse.setExpenseCategoryName("Food");
        expenseResponse.setUserEmail("user@example.com");
        return expenseResponse;
    }

    private ExpenseRequestDto createTestExpenseRequest() {
        ExpenseRequestDto expenseRequest = new ExpenseRequestDto();
        expenseRequest.setAmount(100.0);
        expenseRequest.setDate("01-01-2024");
        expenseRequest.setDescription("Test Expense");
        expenseRequest.setExpenseCategoryId(1L);
        expenseRequest.setUserId(1L);
        return expenseRequest;
    }

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetExpenses {

        @Test
        @DisplayName("Should return a list of expenses")
        void getAll_ShouldReturnListOfExpenses() throws Exception {
            // Given
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();
            when(expenseService.getAll()).thenReturn(List.of(expenseResponse));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$[0].date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$[0].description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$[0].expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$[0].userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1)).getAll();
        }

        @Test
        @DisplayName("Should return not found for non-existing expenses")
        void getAll_ShouldReturnNotFoundForNonExistingExpenses() throws Exception {
            // Given
            given(expenseService.getAll())
                .willThrow(new EntityNotFoundException("Expense", "No expenses found in the system"));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("Expense: No expenses found in the system")
                ));

            verify(expenseService, times(1)).getAll();
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetExpenseById {

        @Test
        @DisplayName("Should return an expense by ID")
        void getById_ShouldReturnExpense() throws Exception {
            // Given
            Long expenseId = 1L;
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();

            when(expenseService.getById(anyLong())).thenReturn(expenseResponse);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", expenseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$.date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$.description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$.expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$.userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1)).getById(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({"999, Expense with ID 999 not found", "1000, Expense with ID 1000 not found"})
        void getById_ShouldReturnNotFoundForNonExistingExpense(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            given(expenseService.getById(id)).willThrow(new EntityNotFoundException("Expense", id));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.timestamp").exists());

            verify(expenseService, times(1)).getById(id);

        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/user/{userId}")
    class GetExpensesByUserId {

        @Test
        @DisplayName("Should return a list of expenses by user ID")
        void getByUserId_ShouldReturnListOfExpenses() throws Exception {
            // Given
            Long userId = 1L;
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();

            when(expenseService.getByUserId(userId)).thenReturn(List.of(expenseResponse));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$[0].date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$[0].description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$[0].expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$[0].userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1)).getByUserId(userId);
        }

        @Test
        @DisplayName("Should return not found when no expenses match the user ID")
        void getByUserId_ShouldReturnNotFoundWhenNoExpensesFound() throws Exception {
            // Given
            Long userId = 1L;
            given(expenseService.getByUserId(userId))
                .willThrow(new EntityNotFoundException(
                    "Expense",
                    "No expenses found for user ID " + userId)
                );

            // When & Then
            mockMvc.perform(get(BASE_URL + "/user/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("Expense: No expenses found for user ID " + userId)
                ));

            verify(expenseService, times(1)).getByUserId(userId);
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/date")
    class GetExpensesByDate {

        @Test
        @DisplayName("Should return a list of expenses by date")
        void getByDate_ShouldReturnListOfExpenses() throws Exception {
            // Given
            String date = "01-01-2024";
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();

            when(expenseService.getByDate(date)).thenReturn(List.of(expenseResponse));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/date").param("date", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$[0].date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$[0].description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$[0].expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$[0].userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1)).getByDate(date);
        }

        @Test
        @DisplayName("Should return not found when no expenses match the date")
        void getByDate_ShouldReturnNotFoundWhenNoExpensesFound() throws Exception {
            // Given
            String date = "10-01-2024";
            given(expenseService.getByDate(date))
                .willThrow(new EntityNotFoundException("Expense", "No expenses found for date " + date));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/date").param("date", date))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("Expense: No expenses found for date " + date)
                ));

            verify(expenseService, times(1)).getByDate(date);
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/expense-category/{expenseCategoryId}")
    class GetExpensesByExpenseCategoryId {

        @Test
        @DisplayName("Should return a list of expenses by expense category ID")
        void getByExpenseCategoryId_ShouldReturnListOfExpenses() throws Exception {
            // Given
            Long expenseCategoryId = 1L;
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();

            when(expenseService.getByExpenseCategoryId(expenseCategoryId))
                .thenReturn(List.of(expenseResponse));

            // When & Then
            mockMvc.perform(get(
                    BASE_URL + "/expense-category/{expenseCategoryId}",
                    expenseCategoryId
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$[0].date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$[0].description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$[0].expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$[0].userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1))
                .getByExpenseCategoryId(expenseCategoryId);
        }

        @Test
        @DisplayName("Should return not found when no expenses match the expense category ID")
        void getByExpenseCategoryId_ShouldReturnNotFoundWhenNoExpensesFound() throws Exception {
            // Given
            Long expenseCategoryId = 1L;
            given(expenseService.getByExpenseCategoryId(expenseCategoryId))
                .willThrow(new EntityNotFoundException(
                    "Expense",
                    "No expenses found for expense category ID " + expenseCategoryId)
                );

            // When & Then
            mockMvc.perform(get(
                    BASE_URL + "/expense-category/{expenseCategoryId}",
                    expenseCategoryId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("Expense: No expenses found for expense category ID " + expenseCategoryId)
                ));

            verify(expenseService, times(1))
                .getByExpenseCategoryId(expenseCategoryId);
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateExpenses {

        @Test
        @DisplayName("Should create a new expense and return it")
        void create_ShouldReturnCreatedExpense() throws Exception {
            // Given
            ExpenseRequestDto expenseRequest = createTestExpenseRequest();
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();

            when(expenseService.create(any(ExpenseRequestDto.class))).thenReturn(expenseResponse);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$.date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$.description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$.expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$.userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1)).create(any(ExpenseRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid amount")
        @CsvSource({
            ", Amount cannot be null",
            "-10.0, Amount must be greater than zero"
        })
        void create_ShouldReturnBadRequestForNegativeAmount(
            Double amount,
            String expectedMessage) throws Exception {

            // Given
            ExpenseRequestDto invalidRequest = createTestExpenseRequest();
            invalidRequest.setAmount(amount);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(expenseService, times(0)).create(any(ExpenseRequestDto.class));

        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid date")
        @CsvSource({
            ", Date cannot be null nor blank",
            "21453-date, Date can only contain numbers and hyphens",
            "01-01-20, Date must have 10 characters format: dd-MM-yyyy"
        })
        void create_ShouldReturnBadRequestForInvalidDate(
            String date,
            String expectedMessage) throws Exception {

            // Given
            ExpenseRequestDto invalidRequest = createTestExpenseRequest();
            invalidRequest.setDate(date);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(expenseService, times(0)).create(any(ExpenseRequestDto.class));

        }

        @Test
        @DisplayName("Should return bad request for not providing expenseCategoryId nor userId")
        void create_ShouldReturnBadRequestForNotProvidingExpenseCategoryIdNorUserId() throws Exception {
            // Given
            ExpenseRequestDto invalidRequest = createTestExpenseRequest();
            invalidRequest.setAmount(15.0);
            invalidRequest.setDate("06-08-2024");
            invalidRequest.setExpenseCategoryId(null);
            invalidRequest.setUserId(null);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                    containsString("ExpenseCategoryId cannot be null"),
                    containsString("UserId cannot be null")
                )));

            verify(expenseService, times(0)).create(any(ExpenseRequestDto.class));
        }

    }

    @Nested
    @DisplayName("PUT " + BASE_URL + "/{id}")
    class UpdateExpenseById {

        @Test
        @DisplayName("Should update an existing expense")
        void update_ShouldReturnUpdatedExpense() throws Exception {
            // Given
            ExpenseRequestDto expenseRequest = createTestExpenseRequest();
            ExpenseResponseDto expenseResponse = createTestExpenseResponse();
            Long expenseId = 1L;

            when(expenseService.update(anyLong(), any(ExpenseRequestDto.class)))
                .thenReturn(expenseResponse);

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", expenseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(expenseResponse.getAmount())))
                .andExpect(jsonPath("$.date", is(expenseResponse.getDate())))
                .andExpect(jsonPath("$.description", is(expenseResponse.getDescription())))
                .andExpect(jsonPath(
                    "$.expenseCategoryName",
                    is(expenseResponse.getExpenseCategoryName())
                ))
                .andExpect(jsonPath("$.userEmail", is(expenseResponse.getUserEmail())));

            verify(expenseService, times(1))
                .update(anyLong(), any(ExpenseRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({"999, Expense with ID 999 not found", "1000, Expense with ID 1000 not found"})
        void update_ShouldReturnNotFoundForNonExistingExpense(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            ExpenseRequestDto expenseRequest = createTestExpenseRequest();
            given(expenseService.update(id, expenseRequest))
                .willThrow(new EntityNotFoundException("Expense", id));

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(expenseService, times(1)).update(id, expenseRequest);

        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteExpenseById {

        @Test
        @DisplayName("Should delete an existing expense")
        void delete_ShouldReturnSuccessMessage() throws Exception {
            // Given
            Long expenseId = 1L;
            doNothing().when(expenseService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", expenseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense deleted successfully"));

            verify(expenseService, times(1)).delete(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({"999, Expense with ID 999 not found", "1000, Expense with ID 1000 not found"})
        void delete_ShouldReturnNotFoundForNonExistingExpense(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            doThrow(new EntityNotFoundException("Expense", id)).when(expenseService).delete(id);

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(expenseService, times(1)).delete(id);

        }

    }

}
