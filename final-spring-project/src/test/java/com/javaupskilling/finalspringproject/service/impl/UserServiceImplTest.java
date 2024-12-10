package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.User;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAll_ShouldReturnListOfUsers_WhenUsersExist() {
        // Given
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserResponseDto> result = userService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowException_WhenNoUsersExist() {
        // Given
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> userService.getAll()
        );

        assertEquals("User: No users found in the system", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnUser_WhenUserExists() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserResponseDto result = userService.getById(userId);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getById_ShouldThrowException_WhenUserDoesNotExist() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> userService.getById(userId)
        );

        assertEquals("User with ID 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getByName_ShouldReturnListOfUsers_WhenUsersExistWithName() {
        // Given
        String name = "john";

        User user1 = new User();
        user1.setName("John");

        User user2 = new User();
        user2.setName("Johnny");

        List<User> users = List.of(user1, user2);
        when(userRepository.findByNameContainingIgnoreCase(name)).thenReturn(users);

        // When
        List<UserResponseDto> result = userService.getByName(name);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Johnny", result.get(1).getName());
        verify(userRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void getByName_ShouldThrowException_WhenNoUsersExistWithName() {
        // Given
        String name = "John";
        when(userRepository.findByNameContainingIgnoreCase(name)).thenReturn(new ArrayList<>());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> userService.getByName(name)
        );

        assertEquals(
            "User: No users with name containing 'John' in the system",
            exception.getMessage()
        );

        verify(userRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void create_ShouldSaveAndReturnUser() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("John");
        userRequestDto.setSurname("Doe");
        userRequestDto.setEmail("john.doe@example.com");

        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserResponseDto result = userService.create(userRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_ShouldUpdateAndReturnUser_WhenUserExists() {
        // Given
        Long userId = 1L;
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("UpdatedName");
        userRequestDto.setSurname("UpdatedSurname");
        userRequestDto.setEmail("updated.email@example.com");

        User existingUser = new User();
        existingUser.setId(userId);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("UpdatedName");
        updatedUser.setSurname("UpdatedSurname");
        updatedUser.setEmail("updated.email@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        UserResponseDto result = userService.update(userId, userRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("UpdatedName", result.getName());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_ShouldThrowException_WhenUserDoesNotExist() {
        // Given
        Long userId = 1L;
        UserRequestDto userRequestDto = new UserRequestDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> userService.update(userId, userRequestDto)
        );

        assertEquals("User with ID 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void delete_ShouldDeleteUser_WhenUserExists() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(expenseRepository.findByUserId(userId)).thenReturn(List.of(new Expense()));

        // When
        userService.delete(userId);

        // Then
        verify(userRepository, times(1)).existsById(userId);
        verify(expenseRepository, times(1)).findByUserId(userId);
        verify(expenseRepository, times(1)).deleteAll(anyList());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void delete_ShouldThrowException_WhenUserDoesNotExist() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> userService.delete(userId)
        );

        assertEquals("User with ID 1 not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(expenseRepository, times(0)).findByUserId(anyLong());
    }

}
