package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.ExpenseCategoryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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

@WebMvcTest(ExpenseCategoryController.class)
class ExpenseCategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ExpenseCategoryService expenseCategoryService;

    @Test
    @DisplayName("GET /api/v1/expense-categories - Should return a list of expense categories")
    void getAll_ShouldReturnListOfExpenseCategories() throws Exception {
        // Given
        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setName("Name test");
        expenseCategoryResponseDto.setDescription("Description test");
        expenseCategoryResponseDto.setExpenses(new ArrayList<>());

        when(expenseCategoryService.getAll()).thenReturn(List.of(expenseCategoryResponseDto));

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("Name test"))
            .andExpect(jsonPath("$[0].description").value("Description test"));

        verify(expenseCategoryService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/v1/expense-categories -" +
        "Should return not found when there are no expense categories in the system")
    void getAll_ShouldReturnNotFoundWhenThereAreNoExpenseCategoriesInTheSystem() throws Exception {
        // Given
        given(expenseCategoryService.getAll()).willThrow(new EntityNotFoundException(
            "ExpenseCategory",
            "No expense categories found in the system"
        ));

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath(
                "$.message",
                is("ExpenseCategory: No expense categories found in the system")
            ));

        verify(expenseCategoryService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/v1/expense-categories/{id} - Should return an expense category by ID")
    void getById_ShouldReturnExpenseCategory() throws Exception {
        // Given
        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setName("Name test");
        expenseCategoryResponseDto.setDescription("Description test");
        expenseCategoryResponseDto.setExpenses(new ArrayList<>());

        when(expenseCategoryService.getById(anyLong())).thenReturn(expenseCategoryResponseDto);

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Name test"))
            .andExpect(jsonPath("$.description").value("Description test"));

        verify(expenseCategoryService, times(1)).getById(anyLong());
    }

    @Test
    @DisplayName("GET /api/v1/expense-categories/{id} -" +
        "Should return not found when when getting non existing expense category")
    void getById_ShouldReturnNotFoundWhenGettingNonExistingExpenseCategory() throws Exception {
        // Given
        Long nonExistingId = 99L;
        given(expenseCategoryService.getById(nonExistingId))
            .willThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId));

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("ExpenseCategory with ID 99 not found")));

        verify(expenseCategoryService, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("GET /api/v1/expense-categories/search -" +
        "Should return a list of expense categories by name")
    void getByName_ShouldReturnListOfExpenseCategories() throws Exception {
        // Given
        String name = "TEST";
        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setName("Name test");
        expenseCategoryResponseDto.setDescription("Description test");
        expenseCategoryResponseDto.setExpenses(new ArrayList<>());

        when(expenseCategoryService.getByName(name)).thenReturn(List.of(expenseCategoryResponseDto));

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories/search")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("Name test"))
            .andExpect(jsonPath("$[0].description").value("Description test"));

        verify(expenseCategoryService, times(1)).getByName(name);
    }

    @Test
    @DisplayName("GET /api/v1/expense-categories/search -" +
        "Should return not found when no expense categories match the name")
    void getByName_ShouldReturnNotFoundWhenNoExpenseCategoriesFound() throws Exception {
        // Given
        String name = "NonExistingName";
        given(expenseCategoryService.getByName(name)).willThrow(new EntityNotFoundException(
            "ExpenseCategory", "No expense categories with name containing '" + name + "' in the system"
        ));

        // When & Then
        mockMvc.perform(get("/api/v1/expense-categories/search")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath(
                "$.message",
                is("ExpenseCategory: No expense categories with name containing '" +
                    name + "' in the system")
            ));

        verify(expenseCategoryService, times(1)).getByName(name);
    }

    @Test
    @DisplayName("POST /api/v1/expense-categories - Should create a new expense category and return it")
    void create_ShouldReturnCreatedExpenseCategory() throws Exception {
        // Given
        ExpenseCategoryRequestDto expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("Name test");
        expenseCategoryRequestDto.setDescription("Description test");
        expenseCategoryRequestDto.setExpenses(new ArrayList<>());

        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setName("Name test");
        expenseCategoryResponseDto.setDescription("Description test");
        expenseCategoryResponseDto.setExpenses(new ArrayList<>());

        when(expenseCategoryService.create(expenseCategoryRequestDto))
            .thenReturn(expenseCategoryResponseDto);

        // When & Then
        mockMvc.perform(post("/api/v1/expense-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseCategoryRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Name test"))
            .andExpect(jsonPath("$.description").value("Description test"))
            .andExpect(jsonPath("$.expenses.size()").value(0));

        verify(expenseCategoryService, times(1)).create(expenseCategoryRequestDto);
    }

    @Test
    @DisplayName("POST /api/v1/expense-categories -" +
        "Should return bad request when creating expense category with invalid data")
    void create_ShouldReturnBadRequestWhenCreatingExpenseCategoryWithInvalidData() throws Exception {
        // Given
        ExpenseCategoryRequestDto expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("");
        expenseCategoryRequestDto.setDescription("%$!&");

        // When & Then
        mockMvc.perform(post("/api/v1/expense-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseCategoryRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasSize(5)))
            .andExpect(jsonPath("$.errors", containsInAnyOrder(
                containsString("Name cannot be null nor blank"),
                containsString("Name can only contain letters and spaces"),
                containsString("Name must have a minimum of 3 letters"),
                containsString("Description can only contain letters, spaces, and the following " +
                    "punctuation marks: . , : ;"),
                containsString("Description must have a minimum of 5 characters")
            )));

        verify(expenseCategoryService, times(0)).create(expenseCategoryRequestDto);
    }

    @Test
    @DisplayName("PUT /api/v1/expense-categories/{id} - Should update an existing expense category")
    void update_ShouldReturnUpdatedExpenseCategory() throws Exception {
        // Given
        ExpenseCategoryRequestDto expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("Name test");
        expenseCategoryRequestDto.setDescription("Description test");
        expenseCategoryRequestDto.setExpenses(new ArrayList<>());

        ExpenseCategoryResponseDto expenseCategoryResponseDto = new ExpenseCategoryResponseDto();
        expenseCategoryResponseDto.setName("Name test");
        expenseCategoryResponseDto.setDescription("Description test");
        expenseCategoryResponseDto.setExpenses(new ArrayList<>());

        when(expenseCategoryService.update(1L, expenseCategoryRequestDto))
            .thenReturn(expenseCategoryResponseDto);

        // When & Then
        mockMvc.perform(put("/api/v1/expense-categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseCategoryRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Name test")))
            .andExpect(jsonPath("$.description", is("Description test")));

        verify(expenseCategoryService, times(1))
            .update(1L, expenseCategoryRequestDto);
    }

    @Test
    @DisplayName("PUT /api/v1/expense-categories/{id} -" +
        "Should return not found when updating non existing expense category")
    void update_shouldReturnNotFoundWhenUpdatingNonExistingExpenseCategory() throws Exception {
        // Given
        Long nonExistingId = 99L;
        ExpenseCategoryRequestDto expenseCategoryRequestDto = new ExpenseCategoryRequestDto();
        expenseCategoryRequestDto.setName("Name test");
        expenseCategoryRequestDto.setDescription("Description test");

        given(expenseCategoryService.update(nonExistingId, expenseCategoryRequestDto))
            .willThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId));

        // When & Then
        mockMvc.perform(put("/api/v1/expense-categories/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseCategoryRequestDto)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("ExpenseCategory with ID 99 not found")));

        verify(expenseCategoryService, times(1))
            .update(nonExistingId, expenseCategoryRequestDto);
    }

    @Test
    @DisplayName("DELETE /api/v1/expense-categories/{id} - Should delete an expense category")
    void delete_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(expenseCategoryService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/v1/expense-categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("ExpenseCategory deleted successfully"));

        verify(expenseCategoryService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("DELETE /api/v1/expense-categories/{id} -" +
        "Should return not found when deleting non existing expense category")
    void delete_ShouldReturnNotFoundWhenDeletingNonExistingExpenseCategory() throws Exception {
        // Given
        Long nonExistingId = 99L;
        doThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId))
            .when(expenseCategoryService).delete(nonExistingId);

        // When & Then
        mockMvc.perform(delete("/api/v1/expense-categories/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("ExpenseCategory with ID 99 not found")));

        verify(expenseCategoryService, times(1)).delete(nonExistingId);
    }

}
