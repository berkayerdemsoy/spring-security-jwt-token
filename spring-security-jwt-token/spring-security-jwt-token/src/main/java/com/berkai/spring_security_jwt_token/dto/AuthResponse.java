package com.berkai.spring_security_jwt_token.dto;

public record AuthResponse(String token, UserDto user) {
}
