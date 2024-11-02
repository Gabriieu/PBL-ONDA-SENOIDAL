package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateNameDTO(
        @NotBlank
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
        String name
) {
}
