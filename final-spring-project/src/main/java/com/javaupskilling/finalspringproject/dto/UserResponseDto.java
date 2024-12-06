package com.javaupskilling.finalspringproject.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponseDto {

    private String name;
    private String surname;
    private String email;
    private List<ExpenseResponseDto> expenses = new ArrayList<>();

}
