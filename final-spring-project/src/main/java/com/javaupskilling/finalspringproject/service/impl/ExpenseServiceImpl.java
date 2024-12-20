package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.exception.EntityNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.model.User;
import com.javaupskilling.finalspringproject.repository.ExpenseCategoryRepository;
import com.javaupskilling.finalspringproject.repository.ExpenseRepository;
import com.javaupskilling.finalspringproject.repository.UserRepository;
import com.javaupskilling.finalspringproject.service.ExpenseService;
import com.javaupskilling.finalspringproject.util.ConversionUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromExpenseEntityToResponseDto;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToExpenseEntity;
import static com.javaupskilling.finalspringproject.util.ConversionUtil.fromRequestDtoToExpenseEntityUpdate;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(
        ExpenseRepository expenseRepository,
        ExpenseCategoryRepository expenseCategoryRepository,
        UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.userRepository = userRepository;

    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getAll() {
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            throw new EntityNotFoundException("Expense", "No expenses found in the system");
        }

        return expenses.stream()
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDto getById(Long id) {
        return expenseRepository.findById(id)
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .orElseThrow(() -> new EntityNotFoundException("Expense", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getByUserId(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        if (expenses.isEmpty()) {
            throw new EntityNotFoundException("Expense", "No expenses found for user ID " + userId);
        }

        return expenses.stream()
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getByDate(String date) {
        List<Expense> expenses = expenseRepository.findByDate(date);
        if (expenses.isEmpty()) {
            throw new EntityNotFoundException("Expense", "No expenses found for date " + date);
        }

        return expenses.stream()
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .toList();
    }

    @Override
    public List<ExpenseResponseDto> getByExpenseCategoryId(Long expenseCategoryId) {
        List<Expense> expenses = expenseRepository.findByExpenseCategoryId(expenseCategoryId);
        if (expenses.isEmpty()) {
            throw new EntityNotFoundException(
                "Expense",
                "No expenses found for expense category ID " + expenseCategoryId
            );
        }

        return expenses.stream()
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .toList();
    }

    @Override
    @Transactional
    public ExpenseResponseDto create(ExpenseRequestDto dto) {
        log.info("Service - Creating expense, DTO received: {}", dto);

        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(dto.getExpenseCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("ExpenseCategory", dto.getExpenseCategoryId()));

        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User", dto.getUserId()));

        Expense savedExpense = expenseRepository.save(fromRequestDtoToExpenseEntity(
            dto,
            expenseCategory,
            user)
        );

        return fromExpenseEntityToResponseDto(savedExpense);
    }

    @Override
    @Transactional
    public ExpenseResponseDto update(Long id, ExpenseRequestDto dto) {
        log.info("Service - Updating expense with ID: {}, DTO received: {}", id, dto);

        Expense existingExpense = expenseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense", id));

        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(dto.getExpenseCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("ExpenseCategory", dto.getExpenseCategoryId()));

        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User", dto.getUserId()));

        Expense updatedExpense = expenseRepository.save(fromRequestDtoToExpenseEntityUpdate(
            existingExpense,
            dto,
            expenseCategory,
            user)
        );

        return fromExpenseEntityToResponseDto(updatedExpense);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense", id);
        }
        expenseRepository.deleteById(id);
    }

}
