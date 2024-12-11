package com.javaupskilling.finalspringproject.dto;

import com.javaupskilling.finalspringproject.model.Expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpenseCategoryRequestDto {

    @NotBlank(message = "Name cannot be null nor blank")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    @Size(min = 3, message = "Name must have a minimum of 3 letters")
    private String name;

    @Pattern(
        regexp = "^[a-zA-Z.,:; ]+$",
        message = "Description can only contain letters, spaces, and the following punctuation marks: . , : ;"
    )
    @Size(min = 5, message = "Description must have a minimum of 5 characters")
    private String description;

    private List<Expense> expenses = new ArrayList<>();

}
