package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotNull(message = "it cannot be null")
    @Size(min = 2, max = 30, message = "it must have between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "it can only contains letters and spaces")
    private String name;

    @NotNull(message = "it cannot be null")
    @Size(min = 2, max = 30, message = "it must have between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "it can only contains letters and spaces")
    private String surname;

    @NotNull(message = "it cannot be null")
    @Email(message = "invalid format")
    private String email;

    @Min(value = 18, message = "it cannot be less than 18")
    @Max(value = 99, message = "it cannot be more than 99")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
