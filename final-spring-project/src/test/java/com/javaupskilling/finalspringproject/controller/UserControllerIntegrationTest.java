package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserRequestDto createUserRequestDto(String name, String surname, String email) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(name);
        userRequestDto.setSurname(surname);
        userRequestDto.setEmail(email);
        return userRequestDto;
    }

    private UserResponseDto createUserResponseDto(String name, String surname, String email) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(name);
        userResponseDto.setSurname(surname);
        userResponseDto.setEmail(email);
        return userResponseDto;
    }

    @Test
    @DisplayName("GET /api/v1/users - Should return a list of users")
    void getAll_ShouldReturnListOfUsers() throws Exception {
        // Given
        UserResponseDto userResponse = createUserResponseDto(
            "John",
            "Doe",
            "john.doe@example.com"
        );

        when(userService.getAll()).thenReturn(List.of(userResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("John"))
            .andExpect(jsonPath("$[0].surname").value("Doe"))
            .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(userService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/v1/users - Should return not found when there are no users in the system")
    void getAll_ShouldReturnNotFoundWhenThereAreNoUsersInTheSystem() throws Exception {
        // Given
        given(userService.getAll())
            .willThrow(new EntityNotFoundException("User", "No users found in the system"));

        // When & Then
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("User: No users found in the system")));

        verify(userService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Should return a user by ID")
    void getById_ShouldReturnUser() throws Exception {
        // Given
        UserResponseDto userResponse = createUserResponseDto(
            "John",
            "Doe",
            "john.doe@example.com"
        );

        when(userService.getById(anyLong())).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"))
            .andExpect(jsonPath("$.surname").value("Doe"))
            .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService, times(1)).getById(anyLong());
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Should return not found when getting non existing user")
    void getById_ShouldReturnNotFoundWhenGettingNonExistingUser() throws Exception {
        // Given
        Long nonExistingId = 999L;
        given(userService.getById(nonExistingId))
            .willThrow(new EntityNotFoundException("User", nonExistingId));

        // When & Then
        mockMvc.perform(get("/api/v1/users/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("User with ID 999 not found")));

        verify(userService, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("GET /api/v1/users/search - Should return a list of users by name")
    void getByName_ShouldReturnListOfUsers() throws Exception {
        // Given
        String searchName = "john";
        UserResponseDto userResponse = createUserResponseDto(
            "John",
            "Doe",
            "john.doe@example.com"
        );

        when(userService.getByName(searchName)).thenReturn(List.of(userResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/users/search")
                .param("name", searchName)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("John"))
            .andExpect(jsonPath("$[0].surname").value("Doe"))
            .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(userService, times(1)).getByName(searchName);
    }

    @Test
    @DisplayName("GET /api/v1/users/search - Should return not found when no users match the name")
    void getByName_ShouldReturnNotFoundWhenNoUsersFound() throws Exception {
        // Given
        String searchName = "NonExistentName";
        given(userService.getByName(searchName)).willThrow(new EntityNotFoundException(
            "User", "No users with name containing '" + searchName + "' in the system"
        ));

        // When & Then
        mockMvc.perform(get("/api/v1/users/search")
                .param("name", searchName)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath(
                "$.message",
                is("User: No users with name containing '" + searchName + "' in the system")
            ));

        verify(userService, times(1)).getByName(searchName);
    }

    @Test
    @DisplayName("POST /api/v1/users - Should create a new user and return it")
    void create_ShouldReturnCreatedUser() throws Exception {
        // Given
        UserRequestDto userRequest = createUserRequestDto(
            "Jane",
            "Doe",
            "jane.doe@example.com"
        );

        UserResponseDto userResponse = createUserResponseDto(
            "Jane",
            "Doe",
            "jane.doe@example.com"
        );

        when(userService.create(any(UserRequestDto.class))).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Jane"))
            .andExpect(jsonPath("$.surname").value("Doe"))
            .andExpect(jsonPath("$.email").value("jane.doe@example.com"));

        verify(userService, times(1)).create(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("POST /api/v1/users - Should return bad request when creating user with invalid data")
    void create_ShouldReturnBadRequestWhenCreatingUserWithInvalidData() throws Exception {
        // Given
        UserRequestDto invalidRequest = new UserRequestDto();
        invalidRequest.setName("");
        invalidRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasSize(6)))
            .andExpect(jsonPath("$.errors", containsInAnyOrder(
                containsString("Name cannot be null nor blank"),
                containsString("Name can only contain letters and spaces"),
                containsString("Name must have a minimum of 2 letters"),
                containsString("Surname cannot be null nor blank"),
                containsString("Email must be a valid one"),
                containsString("Email must have this format: test@example.com")
            )));

        verify(userService, times(0)).create(invalidRequest);
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - Should update an existing user")
    void update_ShouldReturnUpdatedUser() throws Exception {
        // Given
        UserRequestDto userRequest = createUserRequestDto(
            "John",
            "Smith",
            "john.smith@example.com"
        );

        UserResponseDto userResponse = createUserResponseDto(
            "John",
            "Smith",
            "john.smith@example.com"
        );

        when(userService.update(anyLong(), any(UserRequestDto.class))).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"))
            .andExpect(jsonPath("$.surname").value("Smith"))
            .andExpect(jsonPath("$.email").value("john.smith@example.com"));

        verify(userService, times(1)).update(anyLong(), any(UserRequestDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - Should return not found when updating non existing user")
    void update_ShouldReturnNotFoundWhenUpdatingNonExistingUser() throws Exception {
        // Given
        Long nonExistingId = 999L;
        UserRequestDto validRequest = new UserRequestDto();
        validRequest.setName("John");
        validRequest.setSurname("Doe");
        validRequest.setEmail("john.doe@example.com");

        given(userService.update(nonExistingId, validRequest))
            .willThrow(new EntityNotFoundException("User", nonExistingId));

        // When & Then
        mockMvc.perform(put("/api/v1/users/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("User with ID 999 not found")));

        verify(userService, times(1)).update(nonExistingId, validRequest);
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - Should delete a user")
    void delete_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(userService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("User deleted successfully"));

        verify(userService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - Should return not found when deleting non existing user")
    void delete_ShouldReturnNotFoundWhenDeletingNonExistingUser() throws Exception {
        // Given
        Long nonExistingId = 999L;
        doThrow(new EntityNotFoundException("User", nonExistingId))
            .when(userService).delete(nonExistingId);

        // When & Then
        mockMvc.perform(delete("/api/v1/users/{id}", nonExistingId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("User with ID 999 not found")));

        verify(userService, times(1)).delete(nonExistingId);
    }

}
