package com.javaupskilling.finalspringproject.util;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.model.User;

public class ConversionUtil {

    public static UserResponseDto fromUserEntityToResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getName());
        userResponseDto.setSurname(user.getSurname());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setExpenses(user.getExpenses()
            .stream()
            .map(ConversionUtil::fromExpenseEntityToResponseDto)
            .toList());
        return userResponseDto;
    }

    public static User fromRequestDtoToUserEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setSurname(userRequestDto.getSurname());
        user.setEmail(userRequestDto.getEmail());
        user.setExpenses(userRequestDto.getExpenses());
        return user;
    }

    public static User fromRequestDtoToUserEntityUpdate(User existingUser, UserRequestDto userRequestDto) {
        existingUser.setName(userRequestDto.getName());
        existingUser.setSurname(userRequestDto.getSurname());
        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setExpenses(userRequestDto.getExpenses());
        return existingUser;
    }

    public static ExpenseResponseDto fromExpenseEntityToResponseDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setDate(expense.getDate());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setExpenseCategoryName(expense.getExpenseCategory().getName());
        expenseResponseDto.setUserEmail(expense.getUser().getEmail());
        return expenseResponseDto;
    }

    public static Expense fromRequestDtoToExpenseEntity(
        ExpenseRequestDto expenseRequestDto,
        ExpenseCategory expenseCategory,
        User user) {

        Expense expense = new Expense();
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDate(expenseRequestDto.getDate());
        expense.setDescription(expenseRequestDto.getDescription());
        expense.setExpenseCategory(expenseCategory);
        expense.setUser(user);
        return expense;

    }

    public static Expense fromRequestDtoToExpenseEntityUpdate(
        Expense existingExpense,
        ExpenseRequestDto expenseRequestDto,
        ExpenseCategory expenseCategory,
        User user) {

        existingExpense.setAmount(expenseRequestDto.getAmount());
        existingExpense.setDate(expenseRequestDto.getDate());
        existingExpense.setDescription(expenseRequestDto.getDescription());
        existingExpense.setExpenseCategory(expenseCategory);
        existingExpense.setUser(user);
        return existingExpense;

    }

}
