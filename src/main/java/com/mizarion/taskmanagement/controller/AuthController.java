package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.AuthRequest;
import com.mizarion.taskmanagement.dto.AuthResponse;
import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.security.jwt.JWTGenerator;
import com.mizarion.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JWTGenerator jwtGenerator;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("login")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("register")
    @Operation(summary = "Register new user")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerDto) {
        registerDto.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        userService.register(registerDto);
        return ResponseEntity.ok("register user " + registerDto.getEmail());
    }

}
