package com.example.auth_assignment.domain.repository;

import com.example.auth_assignment.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    void save(User user);
}