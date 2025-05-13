package com.example.auth_assignment.application.service;

import com.example.auth_assignment.domain.exception.LoginException;
import com.example.auth_assignment.domain.exception.RegisterException;
import com.example.auth_assignment.domain.model.User;
import com.example.auth_assignment.domain.repository.UserRepositoryPort;
import com.example.auth_assignment.domain.service.AuthServicePort;
import com.example.auth_assignment.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthServicePort {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepositoryPort userRepository;

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new LoginException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new LoginException("Invalid password");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }

    @Override
    public String register(String username, String password, String role) {
        boolean userExists = userRepository.findByUsername(username).isPresent();
        if (userExists) {
            throw new RegisterException("Username already exists");
        }

        User user = new User(null, username, password, role);
        userRepository.save(user);

        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("User not authenticated");
    }
}
