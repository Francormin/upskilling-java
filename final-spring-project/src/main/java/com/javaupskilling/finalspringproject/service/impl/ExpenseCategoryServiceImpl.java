package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.repository.ExpenseCategoryRepository;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.service.ExpenseCategoryService;
import com.javaupskilling.finalspringproject.util.ConversionUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromExpenseCategoryEntityToResponseDto;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToExpenseCategoryEntity;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToExpenseCategoryEntityUpdate;

@Slf4j
@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseCategoryServiceImpl(
        ExpenseCategoryRepository expenseCategoryRepository,
        ExpenseRepository expenseRepository) {

        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseRepository = expenseRepository;

    }

    @Override
    public List<ExpenseCategoryResponseDto> getAll() {
        List<ExpenseCategory> expenseCategories = expenseCategoryRepository.findAll();
        if (expenseCategories.isEmpty()) {
            throw new EntityNotFoundException("ExpenseCategory", "No expense categories found in the system");
        }

        return expenseCategories.stream()
            .map(ConversionUtil::fromExpenseCategoryEntityToResponseDto)
            .toList();
    }

    @Override
    public ExpenseCategoryResponseDto getById(Long id) {
        return expenseCategoryRepository.findById(id)
            .map(ConversionUtil::fromExpenseCategoryEntityToResponseDto)
            .orElseThrow(() -> new EntityNotFoundException("ExpenseCategory", id));
    }

    @Override
    public List<ExpenseCategoryResponseDto> getByName(String name) {
        List<ExpenseCategory> expenseCategoriesFilteredByName =
            expenseCategoryRepository.findByNameContainingIgnoreCase(name);

        if (expenseCategoriesFilteredByName.isEmpty()) {
            throw new EntityNotFoundException(
                "ExpenseCategory",
                "No expense categories with name containing '" + name + "' in the system"
            );
        }

        return expenseCategoriesFilteredByName.stream()
            .map(ConversionUtil::fromExpenseCategoryEntityToResponseDto)
            .toList();
    }

    @Override
    public ExpenseCategoryResponseDto create(ExpenseCategoryRequestDto dto) {
        log.info("Service - Creating expenseCategory, DTO received: {}", dto);

        ExpenseCategory savedExpenseCategory =
            expenseCategoryRepository.save(fromRequestDtoToExpenseCategoryEntity(dto));
        return fromExpenseCategoryEntityToResponseDto(savedExpenseCategory);
    }

    @Override
    public ExpenseCategoryResponseDto update(Long id, ExpenseCategoryRequestDto dto) {
        log.info("Service - Updating expenseCategory with ID: {}, DTO received: {}", id, dto);

        ExpenseCategory existingExpenseCategory = expenseCategoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ExpenseCategory", id));

        ExpenseCategory updatedExpenseCategory = expenseCategoryRepository.save(
            fromRequestDtoToExpenseCategoryEntityUpdate(existingExpenseCategory, dto)
        );

        return fromExpenseCategoryEntityToResponseDto(updatedExpenseCategory);
    }

    @Override
    public void delete(Long id) {
        if (!expenseCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("ExpenseCategory", id);
        }

        List<Expense> expenses = expenseRepository.findByExpenseCategoryId(id);
        if (!expenses.isEmpty()) {
            expenseRepository.deleteAll(expenses);
        }

        expenseCategoryRepository.deleteById(id);
    }

}
