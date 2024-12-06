package com.javaupskilling.finalspringproject.util;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.model.Expense;

public class ConversionUtil {

    public static ExpenseResponseDto fromEntityToResponseDto(Expense expense) {
        ExpenseResponseDto responseDto = new ExpenseResponseDto();
        responseDto.setAmount(expense.getAmount());
        responseDto.setDate(expense.getDate());
        responseDto.setDescription(expense.getDescription());
        responseDto.setExpenseCategoryName(expense.getExpenseCategory().getName());
        responseDto.setUserEmail(expense.getUser().getEmail());
        return responseDto;
    }

    public static ExpenseRequestDto fromEntityToRequestDto(Expense expense) {
        ExpenseRequestDto dto = new ExpenseRequestDto();
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setDescription(expense.getDescription());
        dto.setExpenseCategoryId(expense.getExpenseCategory().getId());
        dto.setUserId(expense.getUser().getId());
        return dto;
    }

}
