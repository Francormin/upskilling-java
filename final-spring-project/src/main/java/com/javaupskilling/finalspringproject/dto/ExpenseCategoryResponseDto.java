package com.javaupskilling.finalspringproject.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpenseCategoryResponseDto {

    private String name;
    private String description;
    private List<ExpenseResponseDto> expenses = new ArrayList<>();

}
