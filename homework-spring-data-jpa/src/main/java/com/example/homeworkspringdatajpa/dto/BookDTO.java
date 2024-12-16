package com.example.homeworkspringdatajpa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank(message = "Title cannot be null nor blank")
    @Size(min = 2, max = 60, message = "Title must have between 2 and 60 characters")
    private String title;

    @NotBlank(message = "Author cannot be null nor blank")
    @Size(min = 2, max = 30, message = "Author must have between 2 and 30 characters")
    private String author;

    @Min(value = 1600, message = "Publish year must be equal or greater than 1600")
    @Max(value = 2024, message = "Publish year must be equal or less than 2024")
    private Integer publishYear;

    @Min(value = 15, message = "Page quantity must be equal or greater than 15")
    @Max(value = 1500, message = "Page quantity must be equal or less than 1500")
    private Integer pageQuantity;

}
