package com.berkai.spring_security_jwt_token.service;

import com.berkai.spring_security_jwt_token.config.PasswordEncoderConfig;
import com.berkai.spring_security_jwt_token.dto.AuthRequest;
import com.berkai.spring_security_jwt_token.dto.AuthResponse;
import com.berkai.spring_security_jwt_token.dto.UserDto;
import com.berkai.spring_security_jwt_token.model.Role;
import com.berkai.spring_security_jwt_token.model.User;
import com.berkai.spring_security_jwt_token.model.UserPrincipal;
import com.berkai.spring_security_jwt_token.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDto registerUser(AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoderConfig.passwordEncoder().encode(request.password()))
                .roles(Set.of(Role.USER))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        User saved = userRepository.save(user);
        return new UserDto(saved.getId(), saved.getUsername(), saved.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet()));
    }

    public AuthResponse loginUser(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        if (auth.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            String token = jwtService.generateToken(userPrincipal);

            User user = userPrincipal.getUser(); // User nesnesini al
            UserDto dto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getRoles().stream().map(Role::name).collect(Collectors.toSet())
            );

            return new AuthResponse(token, dto);
        } else {
            throw new RuntimeException("Invalid login credentials");
        }
    }

}


