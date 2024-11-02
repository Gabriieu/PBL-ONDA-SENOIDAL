package com.example.demo.controller;

import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.dto.user.UserUpdatePasswordDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO user) {
        User userEntity = userService.save(new User(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(userEntity));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getAll(@PageableDefault(size = 5) Pageable page) {
        Page<UserResponseDTO> users = userService.findAll(page).map(UserResponseDTO::new);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == principal.id")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserUpdatePasswordDTO data,
                                               @PathVariable Long id) {
        userService.updatePassword(id,
                data.currentPassword(),
                data.newPassword(),
                data.confirmPassword());

        return ResponseEntity.noContent().build();
    }
}
