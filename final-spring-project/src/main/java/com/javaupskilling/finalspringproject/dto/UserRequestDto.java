package com.javaupskilling.finalspringproject.dto;

import com.javaupskilling.finalspringproject.model.Expense;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDto {

    @NotBlank(message = "Name cannot be null nor blank")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    @Size(min = 2, message = "Name must have a minimum of 2 letters")
    private String name;

    @NotBlank(message = "Surname cannot be null nor blank")
    @Pattern(
        regexp = "^[\\p{L}\\s'-]+$",
        message = "Surname can only contain letters, spaces, hyphens, and apostrophes"
    )
    @Size(min = 2, message = "Surname must have a minimum of 2 characters")
    private String surname;

    @Email(message = "Email must be a valid one")
    @Pattern(
        regexp = "^[\\w-.]+@(\\w{3,}.)+\\w{2,4}$",
        message = "Email must have this format: test@example.com"
    )
    private String email;

    private List<Expense> expenses = new ArrayList<>();

}
