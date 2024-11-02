package com.example.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Invalid email")
        @JsonProperty("email")
        String username,
        @NotBlank
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}
