package com.example.demo.controller;

import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.dto.user.UserUpdateNameDTO;
import com.example.demo.dto.user.UserUpdatePasswordDTO;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUserDetails;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfoVBA(@AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());

        String funcao;
        if (user.getRole().equals(User.Role.ROLE_ADMIN)) {
            funcao = "Administrador";
        } else {
            funcao = "Usuário";
        }
        return ResponseEntity.ok(String.format("%s; %s; %s", user.getName(), user.getUsername(), funcao));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserUpdatePasswordDTO data,
                                               @AuthenticationPrincipal JwtUserDetails userDetails) {
        userService.updatePassword(userDetails.getId(),
                data.currentPassword(),
                data.newPassword(),
                data.confirmPassword());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateName(@RequestBody @Valid UserUpdateNameDTO data,
                                           @AuthenticationPrincipal JwtUserDetails userDetails) {
        userService.updateName(userDetails.getId(), data.name());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String password,@AuthenticationPrincipal JwtUserDetails userDetails) {
        userService.delete(userDetails.getId(), password);
        return ResponseEntity.noContent().build();
    }
}
