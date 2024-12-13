package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.service.UserService;

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

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private static final String BASE_URL = "/api/v1/users";

    private UserRequestDto createUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("John");
        userRequestDto.setSurname("Doe");
        userRequestDto.setEmail("john.doe@example.com");
        return userRequestDto;
    }

    private UserResponseDto createUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName("John");
        userResponseDto.setSurname("Doe");
        userResponseDto.setEmail("john.doe@example.com");
        return userResponseDto;
    }

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetUsers {

        @Test
        @DisplayName("Should return a list of users")
        void getAll_ShouldReturnListOfUsers() throws Exception {
            // Given
            UserResponseDto userResponse = createUserResponseDto();
            when(userService.getAll()).thenReturn(List.of(userResponse));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(userResponse.getName()))
                .andExpect(jsonPath("$[0].surname").value(userResponse.getSurname()))
                .andExpect(jsonPath("$[0].email").value(userResponse.getEmail()));

            verify(userService, times(1)).getAll();
        }

        @Test
        @DisplayName("Should return not found for non-existing users")
        void getAll_ShouldReturnNotFoundForNonExistingUsers() throws Exception {
            // Given
            given(userService.getAll())
                .willThrow(new EntityNotFoundException("User", "No users found in the system"));

            // When & Then
            mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("User: No users found in the system")
                ));

            verify(userService, times(1)).getAll();
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetUserById {

        @Test
        @DisplayName("Should return a user by ID")
        void getById_ShouldReturnUser() throws Exception {
            // Given
            Long userId = 1L;
            UserResponseDto userResponse = createUserResponseDto();

            when(userService.getById(anyLong())).thenReturn(userResponse);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.surname").value(userResponse.getSurname()))
                .andExpect(jsonPath("$.email").value(userResponse.getEmail()));

            verify(userService, times(1)).getById(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({"999, User with ID 999 not found", "1000, User with ID 1000 not found"})
        void getById_ShouldReturnNotFoundForNonExistingUser(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            given(userService.getById(id)).willThrow(new EntityNotFoundException("User", id));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.timestamp").exists());

            verify(userService, times(1)).getById(id);

        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/search")
    class GetUsersByName {

        @Test
        @DisplayName("Should return a list of users by name")
        void getByName_ShouldReturnListOfUsers() throws Exception {
            // Given
            String name = "john";
            UserResponseDto userResponse = createUserResponseDto();

            when(userService.getByName(name)).thenReturn(List.of(userResponse));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/search").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(userResponse.getName()))
                .andExpect(jsonPath("$[0].surname").value(userResponse.getSurname()))
                .andExpect(jsonPath("$[0].email").value(userResponse.getEmail()));

            verify(userService, times(1)).getByName(name);
        }

        @Test
        @DisplayName("Should return not found when no users match the name")
        void getByName_ShouldReturnNotFoundWhenNoUsersFound() throws Exception {
            // Given
            String name = "NonExistingName";
            given(userService.getByName(name))
                .willThrow(new EntityNotFoundException(
                    "User",
                    "No users with name containing '" + name + "' in the system"
                ));

            // When & Then
            mockMvc.perform(get(BASE_URL + "/search").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(
                    "$.message",
                    is("User: No users with name containing '" + name + "' in the system")
                ));

            verify(userService, times(1)).getByName(name);
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateUsers {

        @Test
        @DisplayName("Should create a new user and return it")
        void create_ShouldReturnCreatedUser() throws Exception {
            // Given
            UserRequestDto userRequest = createUserRequestDto();
            UserResponseDto userResponse = createUserResponseDto();

            when(userService.create(any(UserRequestDto.class))).thenReturn(userResponse);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.surname").value(userResponse.getSurname()))
                .andExpect(jsonPath("$.email").value(userResponse.getEmail()));

            verify(userService, times(1)).create(any(UserRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid name")
        @CsvSource({
            ", Name cannot be null nor blank",
            "6in valid7, Name can only contain letters and spaces",
            "a, Name must have a minimum of 2 letters"
        })
        void create_ShouldReturnBadRequestForInvalidName(
            String name,
            String expectedMessage) throws Exception {

            // Given
            UserRequestDto invalidRequest = createUserRequestDto();
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

            verify(userService, times(0)).create(any(UserRequestDto.class));

        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid surname")
        @CsvSource({
            ", Surname cannot be null nor blank",
            "6in valid7, Surname can only contain letters, spaces, hyphens, and apostrophes",
            "a, Surname must have a minimum of 2 characters"
        })
        void create_ShouldReturnBadRequestForInvalidSurname(
            String surname,
            String expectedMessage) throws Exception {

            // Given
            UserRequestDto invalidRequest = createUserRequestDto();
            invalidRequest.setSurname(surname);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(userService, times(0)).create(any(UserRequestDto.class));

        }

        @ParameterizedTest
        @DisplayName("Should return bad request for invalid email")
        @CsvSource({
            "not-valid@smt@oth.com, Email must be a valid one",
            "invalid.email@abc, Email must have this format: test@example.com"
        })
        void create_ShouldReturnBadRequestForInvalidEmail(
            String email,
            String expectedMessage) throws Exception {

            // Given
            UserRequestDto invalidRequest = createUserRequestDto();
            invalidRequest.setEmail(email);

            // When & Then
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                    "$.errors",
                    containsInAnyOrder(containsString(expectedMessage))
                ));

            verify(userService, times(0)).create(any(UserRequestDto.class));

        }

    }

    @Nested
    @DisplayName("PUT " + BASE_URL + "/{id}")
    class UpdateUserById {

        @Test
        @DisplayName("Should update an existing user")
        void update_ShouldReturnUpdatedUser() throws Exception {
            // Given
            UserRequestDto userRequest = createUserRequestDto();
            UserResponseDto userResponse = createUserResponseDto();
            Long userId = 1L;

            when(userService.update(anyLong(), any(UserRequestDto.class))).thenReturn(userResponse);

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.surname").value(userResponse.getSurname()))
                .andExpect(jsonPath("$.email").value(userResponse.getEmail()));

            verify(userService, times(1))
                .update(anyLong(), any(UserRequestDto.class));
        }

        @ParameterizedTest
        @DisplayName("Should return not found when updating non existing user")
        @CsvSource({"999, User with ID 999 not found", "1000, User with ID 1000 not found"})
        void update_ShouldReturnNotFoundWhenUpdatingNonExistingUser(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            UserRequestDto userRequest = createUserRequestDto();
            given(userService.update(id, userRequest))
                .willThrow(new EntityNotFoundException("User", id));

            // When & Then
            mockMvc.perform(put(BASE_URL + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(userService, times(1)).update(id, userRequest);

        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteUserById {

        @Test
        @DisplayName("Should delete an existing user")
        void delete_ShouldReturnSuccessMessage() throws Exception {
            // Given
            Long userId = 1L;
            doNothing().when(userService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

            verify(userService, times(1)).delete(anyLong());
        }

        @ParameterizedTest
        @DisplayName("Should return not found for non-existing ID")
        @CsvSource({"999, User with ID 999 not found", "1000, User with ID 1000 not found"})
        void delete_ShouldReturnNotFoundForNonExistingUser(
            Long id,
            String expectedMessage) throws Exception {

            // Given
            doThrow(new EntityNotFoundException("User", id)).when(userService).delete(id);

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedMessage)));

            verify(userService, times(1)).delete(id);

        }

    }

}
