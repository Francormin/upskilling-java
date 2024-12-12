package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.ExpenseCategoryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    private static final String BASE_URL = "/api/v1/expense-categories";

    private ExpenseCategoryResponseDto createExpenseCategoryResponse() {
        ExpenseCategoryResponseDto dto = new ExpenseCategoryResponseDto();
        dto.setName("Name test");
        dto.setDescription("Description test");
        dto.setExpenses(List.of());
        return dto;
    }

    private ExpenseCategoryRequestDto createExpenseCategoryRequest() {
        ExpenseCategoryRequestDto dto = new ExpenseCategoryRequestDto();
        dto.setName("Name test");
        dto.setDescription("Description test");
        dto.setExpenses(List.of());
        return dto;
    }

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetExpenseCategories {

        @Test
        @DisplayName("Should return a list of expense categories")
        void getAll_ShouldReturnListOfExpenseCategories() throws Exception {
            // Given
            ExpenseCategoryResponseDto expenseCategoryResponseDto = createExpenseCategoryResponse();
            when(expenseCategoryService.getAll()).thenReturn(List.of(expenseCategoryResponseDto));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name")
                    .value(expenseCategoryResponseDto.getName()))
                .andExpect(jsonPath("$[0].description")
                    .value(expenseCategoryResponseDto.getDescription()));

            verify(expenseCategoryService, times(1)).getAll();
        }

        @Test
        @DisplayName("Should return not found when there are no expense categories in the system")
        void getAll_ShouldReturnNotFoundWhenThereAreNoExpenseCategoriesInTheSystem() throws Exception {
            // Given
            given(expenseCategoryService.getAll()).willThrow(new EntityNotFoundException(
                "ExpenseCategory",
                "No expense categories found in the system"
            ));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("ExpenseCategory: No expense categories found in the system")
                ));

            verify(expenseCategoryService, times(1)).getAll();
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetExpenseCategoryById {

        @Test
        @DisplayName("Should return an expense category by ID")
        void getById_ShouldReturnExpenseCategory() throws Exception {
            // Given
            ExpenseCategoryResponseDto expenseCategoryResponseDto = createExpenseCategoryResponse();
            when(expenseCategoryService.getById(anyLong())).thenReturn(expenseCategoryResponseDto);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                    .value(expenseCategoryResponseDto.getName()))
                .andExpect(jsonPath("$.description")
                    .value(expenseCategoryResponseDto.getDescription()));

            verify(expenseCategoryService, times(1)).getById(anyLong());
        }

        @Test
        @DisplayName("Should return not found when when getting non existing expense category")
        void getById_ShouldReturnNotFoundWhenGettingNonExistingExpenseCategory() throws Exception {
            // Given
            Long nonExistingId = 99L;
            given(expenseCategoryService.getById(nonExistingId))
                .willThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("ExpenseCategory with ID 99 not found")
                ));

            verify(expenseCategoryService, times(1)).getById(nonExistingId);
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/search")
    class GetExpenseCategoriesByName {

        @Test
        @DisplayName("Should return a list of expense categories by name")
        void getByName_ShouldReturnListOfExpenseCategories() throws Exception {
            // Given
            String name = "TEST";
            ExpenseCategoryResponseDto expenseCategoryResponseDto = createExpenseCategoryResponse();

            when(expenseCategoryService.getByName(name))
                .thenReturn(List.of(expenseCategoryResponseDto));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/search").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name")
                    .value(expenseCategoryResponseDto.getName()))
                .andExpect(jsonPath("$[0].description")
                    .value(expenseCategoryResponseDto.getDescription()));

            verify(expenseCategoryService, times(1)).getByName(name);
        }

        @Test
        @DisplayName("Should return not found when no expense categories match the name")
        void getByName_ShouldReturnNotFoundWhenNoExpenseCategoriesFound() throws Exception {
            // Given
            String name = "NonExistingName";
            given(expenseCategoryService.getByName(name))
                .willThrow(new EntityNotFoundException(
                    "ExpenseCategory",
                    "No expense categories with name containing '" + name + "' in the system"
                ));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/search").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("ExpenseCategory: No expense categories with name containing '" +
                        name + "' in the system")
                ));

            verify(expenseCategoryService, times(1)).getByName(name);
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateExpenseCategories {

        @Test
        @DisplayName("Should create a new expense category and return it")
        void create_ShouldReturnCreatedExpenseCategory() throws Exception {
            // Given
            ExpenseCategoryRequestDto expenseCategoryRequest = createExpenseCategoryRequest();
            ExpenseCategoryResponseDto expenseCategoryResponseDto = createExpenseCategoryResponse();

            when(expenseCategoryService.create(expenseCategoryRequest))
                .thenReturn(expenseCategoryResponseDto);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                    .value(expenseCategoryResponseDto.getName()))
                .andExpect(jsonPath("$.description")
                    .value(expenseCategoryResponseDto.getDescription()))
                .andExpect(jsonPath("$.expenses.size()")
                    .value(0));

            verify(expenseCategoryService, times(1))
                .create(expenseCategoryRequest);
        }

        @Test
        @DisplayName("Should return bad request for invalid data")
        void create_ShouldReturnBadRequestForInvalidData() throws Exception {
            // Given
            ExpenseCategoryRequestDto expenseCategoryRequest = createExpenseCategoryRequest();
            expenseCategoryRequest.setName("");
            expenseCategoryRequest.setDescription("%$!&");

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(5)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                    containsString("Name cannot be null nor blank"),
                    containsString("Name can only contain letters and spaces"),
                    containsString("Name must have a minimum of 3 letters"),
                    containsString("Description can only contain letters, spaces, and the " +
                        "following punctuation marks: . , : ;"),
                    containsString("Description must have a minimum of 5 characters")
                )));

            verify(expenseCategoryService, times(0))
                .create(expenseCategoryRequest);
        }

    }

    @Nested
    @DisplayName("PUT " + BASE_URL + "/{id}")
    class UpdateExpenseById {

        @Test
        @DisplayName("Should update an existing expense category")
        void update_ShouldReturnUpdatedExpenseCategory() throws Exception {
            // Given
            ExpenseCategoryRequestDto expenseCategoryRequest = createExpenseCategoryRequest();
            ExpenseCategoryResponseDto expenseCategoryResponse = createExpenseCategoryResponse();

            when(expenseCategoryService.update(1L, expenseCategoryRequest))
                .thenReturn(expenseCategoryResponse);

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(expenseCategoryResponse.getName())))
                .andExpect(jsonPath(
                    "$.description",
                    is(expenseCategoryResponse.getDescription())
                ));

            verify(expenseCategoryService, times(1))
                .update(1L, expenseCategoryRequest);
        }

        @Test
        @DisplayName("Should return not found when updating non existing expense category")
        void update_shouldReturnNotFoundWhenUpdatingNonExistingExpenseCategory() throws Exception {
            // Given
            Long nonExistingId = 99L;
            ExpenseCategoryRequestDto expenseCategoryRequest = createExpenseCategoryRequest();

            given(expenseCategoryService.update(nonExistingId, expenseCategoryRequest))
                .willThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId));

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", nonExistingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("ExpenseCategory with ID 99 not found")
                ));

            verify(expenseCategoryService, times(1))
                .update(nonExistingId, expenseCategoryRequest);
        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteExpenseById {

        @Test
        @DisplayName("Should delete an expense category")
        void delete_ShouldReturnSuccessMessage() throws Exception {
            // Given
            doNothing().when(expenseCategoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("ExpenseCategory deleted successfully"));

            verify(expenseCategoryService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("Should return not found when deleting non existing expense category")
        void delete_ShouldReturnNotFoundWhenDeletingNonExistingExpenseCategory() throws Exception {
            // Given
            Long nonExistingId = 99L;
            doThrow(new EntityNotFoundException("ExpenseCategory", nonExistingId))
                .when(expenseCategoryService).delete(nonExistingId);

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("ExpenseCategory with ID 99 not found")
                ));

            verify(expenseCategoryService, times(1)).delete(nonExistingId);
        }

    }

}
