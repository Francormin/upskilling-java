package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.ExpenseCategoryService;

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
        @DisplayName("Should return not found for non-existing expense categories")
        void getAll_ShouldReturnNotFoundForNonExistingExpenseCategories() throws Exception {
            // Given
            given(expenseCategoryService.getAll())
                .willThrow(new EntityNotFoundException(
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
            Long expenseCategoryId = 1L;
            ExpenseCategoryResponseDto expenseCategoryResponseDto = createExpenseCategoryResponse();

            when(expenseCategoryService.getById(anyLong())).thenReturn(expenseCategoryResponseDto);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", expenseCategoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                    .value(expenseCategoryResponseDto.getName()))
                .andExpect(jsonPath("$.description")
                    .value(expenseCategoryResponseDto.getDescription()));

            verify(expenseCategoryService, times(1)).getById(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({
            "99, ExpenseCategory with ID 99 not found",
            "100, ExpenseCategory with ID 100 not found"
        })
        void getById_ShouldReturnNotFoundForNonExistingExpenseCategory(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            given(expenseCategoryService.getById(id))
                .willThrow(new EntityNotFoundException("ExpenseCategory", id));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.timestamp").exists());

            verify(expenseCategoryService, times(1)).getById(id);

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

            when(expenseCategoryService.create(any(ExpenseCategoryRequestDto.class)))
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
                .andExpect(jsonPath("$.expenses.size()").value(0));

            verify(expenseCategoryService, times(1))
                .create(any(ExpenseCategoryRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid name")
        @CsvSource({
            ", Name cannot be null nor blank",
            "43invalid name36, Name can only contain letters and spaces",
            "no, Name must have a minimum of 3 letters"
        })
        void create_ShouldReturnBadRequestForInvalidName(
            String name,
            String expectedMessage) throws Exception {

            // Given
            ExpenseCategoryRequestDto invalidRequest = createExpenseCategoryRequest();
            invalidRequest.setName(name);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(expenseCategoryService, times(0))
                .create(any(ExpenseCategoryRequestDto.class));

        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid description")
        @CsvSource({
            "%$!&=, Description can only contain letters, spaces, and the following punctuation " +
                "marks: . , : ;",
            "soon, Description must have a minimum of 5 characters"
        })
        void create_ShouldReturnBadRequestForInvalidDescription(
            String description,
            String expectedMessage) throws Exception {

            // Given
            ExpenseCategoryRequestDto invalidRequest = createExpenseCategoryRequest();
            invalidRequest.setDescription(description);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(expenseCategoryService, times(0))
                .create(any(ExpenseCategoryRequestDto.class));

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
            Long expenseCategoryId = 1L;

            when(expenseCategoryService.update(anyLong(), any(ExpenseCategoryRequestDto.class)))
                .thenReturn(expenseCategoryResponse);

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", expenseCategoryId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                    "$.name",
                    is(expenseCategoryResponse.getName())
                ))
                .andExpect(jsonPath(
                    "$.description",
                    is(expenseCategoryResponse.getDescription())
                ));

            verify(expenseCategoryService, times(1))
                .update(anyLong(), any(ExpenseCategoryRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({
            "99, ExpenseCategory with ID 99 not found",
            "100, ExpenseCategory with ID 100 not found"
        })
        void update_shouldReturnNotFoundForNonExistingExpenseCategory(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            ExpenseCategoryRequestDto expenseCategoryRequest = createExpenseCategoryRequest();
            given(expenseCategoryService.update(id, expenseCategoryRequest))
                .willThrow(new EntityNotFoundException("ExpenseCategory", id));

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expenseCategoryRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(expenseCategoryService, times(1))
                .update(id, expenseCategoryRequest);

        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteExpenseById {

        @Test
        @DisplayName("Should delete an existing expense category")
        void delete_ShouldReturnSuccessMessage() throws Exception {
            // Given
            Long expenseCategoryId = 1L;
            doNothing().when(expenseCategoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", expenseCategoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("ExpenseCategory deleted successfully"));

            verify(expenseCategoryService, times(1)).delete(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({
            "99, ExpenseCategory with ID 99 not found",
            "100, ExpenseCategory with ID 100 not found"
        })
        void delete_ShouldReturnNotFoundForNonExistingExpenseCategory(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            doThrow(new EntityNotFoundException("ExpenseCategory", id))
                .when(expenseCategoryService).delete(id);

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(expenseCategoryService, times(1)).delete(id);

        }

    }

}
