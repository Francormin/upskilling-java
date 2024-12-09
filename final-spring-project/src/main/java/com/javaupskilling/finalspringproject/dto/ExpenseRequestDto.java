package com.javaupskilling.finalspringproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExpenseRequestDto {

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Date cannot be null nor blank")
    @Size(min = 10, max = 10, message = "Date must have 10 characters format: dd-MM-yyyy")
    private String date;

    @Size(min = 5, message = "Description must have a minimum of 5 characters")
    private String description;

    @NotNull(message = "ExpenseCategoryId cannot be null")
    private Long expenseCategoryId;

    @NotNull(message = "UserId cannot be null")
    private Long userId;

}