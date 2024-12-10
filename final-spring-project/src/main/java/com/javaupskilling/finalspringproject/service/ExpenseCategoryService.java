package com.javaupskilling.finalspringproject.service;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategoryResponseDto> getAll();

    ExpenseCategoryResponseDto getById(Long id);

    List<ExpenseCategoryResponseDto> getByName(String name);

    ExpenseCategoryResponseDto create(ExpenseCategoryRequestDto dto);

    ExpenseCategoryResponseDto update(Long id, ExpenseCategoryRequestDto dto);

    void delete(Long id);

}
