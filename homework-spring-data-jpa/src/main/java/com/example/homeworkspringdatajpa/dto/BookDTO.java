package com.example.homeworkspringdatajpa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

    public BookDTO() {
    }

    public BookDTO(String title, String author, Integer publishYear, Integer pageQuantity) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.pageQuantity = pageQuantity;
    }

    public @NotBlank(message = "Title cannot be null nor blank") @Size(min = 2, max = 60, message = "Title must have between 2 and 60 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be null nor blank") @Size(min = 2, max = 60, message = "Title must have between 2 and 60 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Author cannot be null nor blank") @Size(min = 2, max = 30, message = "Author must have between 2 and 30 characters") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author cannot be null nor blank") @Size(min = 2, max = 30, message = "Author must have between 2 and 30 characters") String author) {
        this.author = author;
    }

    public @Min(value = 1600, message = "Publish year must be equal or greater than 1600") @Max(value = 2024, message = "Publish year must be equal or less than 2024") Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(@Min(value = 1600, message = "Publish year must be equal or greater than 1600") @Max(value = 2024, message = "Publish year must be equal or less than 2024") Integer publishYear) {
        this.publishYear = publishYear;
    }

    public @Min(value = 15, message = "Page quantity must be equal or greater than 15") @Max(value = 1500, message = "Page quantity must be equal or less than 1500") Integer getPageQuantity() {
        return pageQuantity;
    }

    public void setPageQuantity(@Min(value = 15, message = "Page quantity must be equal or greater than 15") @Max(value = 1500, message = "Page quantity must be equal or less than 1500") Integer pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
            "title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", publishYear=" + publishYear +
            ", pageQuantity=" + pageQuantity +
            '}';
    }

}
