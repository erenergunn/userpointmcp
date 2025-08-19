package com.userpoint.controller;

import com.userpoint.dto.JwtResponseDto;
import com.userpoint.dto.UserLoginDto;
import com.userpoint.dto.UserRegistrationDto;
import com.userpoint.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration and login endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<JwtResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            JwtResponseDto response = authService.registerUser(registrationDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<JwtResponseDto> authenticateUser(@Valid @RequestBody UserLoginDto loginDto) {
        try {
            JwtResponseDto response = authService.authenticateUser(loginDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
