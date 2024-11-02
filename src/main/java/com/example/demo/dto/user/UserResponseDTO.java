package com.example.demo.dto.user;


import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
        Long id,
        @JsonProperty("email")
        String username,
        String role
) {
    public UserResponseDTO(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getRole().name().substring("ROLE_".length()));
    }
}
