package com.javaupskilling.finalspringproject.service;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getById(Long id);

    List<UserResponseDto> getByName(String name);

    UserResponseDto create(UserRequestDto dto);

    UserResponseDto update(Long id, UserRequestDto dto);

    void delete(Long id);

}
