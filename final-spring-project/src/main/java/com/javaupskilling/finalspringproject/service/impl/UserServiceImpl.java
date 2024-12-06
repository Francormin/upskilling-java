package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.User;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.repository.UserRepository;
import com.javaupskilling.finalspringproject.service.UserService;
import com.javaupskilling.finalspringproject.util.ConversionUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToUserEntity;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToUserEntityUpdate;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromUserEntityToResponseDto;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public UserServiceImpl(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("User", "No users found in the system");
        }

        return users.stream()
            .map(ConversionUtil::fromUserEntityToResponseDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(Long id) {
        return userRepository.findById(id)
            .map(ConversionUtil::fromUserEntityToResponseDto)
            .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public List<UserResponseDto> getByName(String name) {
        List<User> usersFilteredByName = userRepository.findByNameContainingIgnoreCase(name);
        if (usersFilteredByName.isEmpty()) {
            throw new EntityNotFoundException(
                "User",
                "No users with name containing '" + name + "' in the system"
            );
        }

        return usersFilteredByName.stream()
            .map(ConversionUtil::fromUserEntityToResponseDto)
            .toList();
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        log.info("Service - Creating user, DTO received: {}", dto);

        User savedUser = userRepository.save(fromRequestDtoToUserEntity(dto));
        return fromUserEntityToResponseDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        log.info("Service - Updating user with ID: {}, DTO received: {}", id, dto);

        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User", id));

        User updatedUser = userRepository.save(fromRequestDtoToUserEntityUpdate(existingUser, dto));
        return fromUserEntityToResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User", id);
        }

        List<Expense> expenses = expenseRepository.findByUserId(id);
        if (!expenses.isEmpty()) {
            expenseRepository.deleteAll(expenses);
        }

        userRepository.deleteById(id);
    }

}
