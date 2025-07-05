package com.berkai.spring_security_jwt_token.controller;

import com.berkai.spring_security_jwt_token.dto.AuthResponse;
import com.berkai.spring_security_jwt_token.dto.AuthRequest;
import com.berkai.spring_security_jwt_token.dto.UserDto;
import com.berkai.spring_security_jwt_token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody AuthRequest request) {
        UserDto created = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String hello(){
        return "This section is authenticated";
    }
    @GetMapping("/")
    public String index(){
        return "This section can be reach for anyone";
    }
    @GetMapping("/user")
    public String forUser(){
        return "This section for USER role";
    }
    @GetMapping("/admin")
    public String forAdmin(){
        return "This section for ADMIN role";
    }
}

