package com.javaupskilling.finalspringproject.service;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponseDto> getAll();

    ExpenseResponseDto getById(Long id);

    List<ExpenseResponseDto> getByUserId(Long userId);

    List<ExpenseResponseDto> getByDate(String date);

    List<ExpenseResponseDto> getByExpenseCategoryId(Long expenseCategoryId);

    ExpenseResponseDto create(ExpenseRequestDto dto);

    ExpenseResponseDto update(Long id, ExpenseRequestDto dto);

    void delete(Long id);

}
