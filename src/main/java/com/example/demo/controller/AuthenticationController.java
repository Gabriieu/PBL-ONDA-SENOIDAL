package com.example.demo.controller;

import com.example.demo.dto.user.UserLoginDTO;
import com.example.demo.exception.ErrorMessage;
import com.example.demo.jwt.JwtToken;
import com.example.demo.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO loginData, HttpServletRequest request) {
        log.info("Authenticating user: {}", loginData.username());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginData.username(), loginData.password());
            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(loginData.username());

            return ResponseEntity.ok(token);
        } catch (
                AuthenticationException exception) {
            log.warn("Error authenticating user: {}", loginData.username());
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credentials"));
        }
    }

    @PostMapping("/login/vba")
    public ResponseEntity<?> authenticateVBA(@RequestBody @Valid UserLoginDTO loginData, HttpServletRequest request) {
        log.info("Authenticating user: {}", loginData.username());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginData.username(), loginData.password());
            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(loginData.username());

            return ResponseEntity.ok(token.getToken());
        } catch (
                AuthenticationException exception) {
            log.warn("Error authenticating user: {}", loginData.username());
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credentials"));
        }
    }
}
