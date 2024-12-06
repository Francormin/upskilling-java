package com.javaupskilling.finalspringproject.dto;

import com.javaupskilling.finalspringproject.model.Expense;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDto {

    @NotBlank(message = "Name cannot be null nor blank")
    private String name;

    @NotBlank(message = "Surname cannot be null nor blank")
    private String surname;

    @Email(message = "Email must be a valid one")
    private String email;

    private List<Expense> expenses = new ArrayList<>();

}
