package com.berkai.spring_security_jwt_token.dto;


import java.util.Set;

public record UserDto(Long id, String username, Set<String > roles) {
}
