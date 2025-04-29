package com.example.auth_assignment.application.service;

import com.example.auth_assignment.domain.exception.LoginException;
import com.example.auth_assignment.domain.exception.RegisterException;
import com.example.auth_assignment.domain.model.User;
import com.example.auth_assignment.adapter.out.repository.JpaUserRepository; // import JpaUserRepository
import com.example.auth_assignment.domain.service.AuthServicePort;
import com.example.auth_assignment.infrastructure.entity.UserEntity;
import com.example.auth_assignment.infrastructure.security.JwtTokenProvider;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthServicePort {

    private final JwtTokenProvider jwtTokenProvider;
    private final JpaUserRepository userRepository;

    // ใช้ constructor injection เพื่อให้ Spring inject `JpaUserRepository`
    public AuthServiceImplementation(JwtTokenProvider jwtTokenProvider, JpaUserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .map(userEntity -> new User(null, userEntity.getUsername(), userEntity.getPassword(),
                        userEntity.getRole()))
                .orElseThrow(() -> new LoginException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new LoginException("Invalid username or password");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }

    @Override
    public void register(String username, String password, String role) {
        boolean userExists = userRepository.findByUsername(username).isPresent();
        if (userExists) {
            throw new RegisterException("Username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password); // ในการใช้งานจริงควรเข้ารหัส password เช่น BCrypt
        userEntity.setRole(role);

        userRepository.save(userEntity);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username)
                    .map(userEntity -> new User(null, userEntity.getUsername(), userEntity.getPassword(),
                            userEntity.getRole()))
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("User not authenticated");
    }
}
