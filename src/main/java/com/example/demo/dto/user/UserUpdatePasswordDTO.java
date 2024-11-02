package com.example.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordDTO(

        @JsonProperty("current_password")
        @NotNull
        @NotBlank
        String currentPassword,

        @JsonProperty("new_password")
        @NotNull
        @NotBlank
        @Size(min = 6, message = "New password must be at least 6 characters long", max = 32)
        String newPassword,

        @JsonProperty("confirm_password")
        @NotNull
        @NotBlank
        @Size(min = 6, message = "Confirm password must be at least 6 characters long", max = 32)
        String confirmPassword
) {
}
