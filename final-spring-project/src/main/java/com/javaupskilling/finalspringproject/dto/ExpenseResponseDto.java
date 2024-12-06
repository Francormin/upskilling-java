package com.javaupskilling.finalspringproject.dto;

import lombok.Data;

@Data
public class ExpenseResponseDto {

    private Double amount;
    private String date;
    private String description;
    private String expenseCategoryName;
    private String userEmail;

}
