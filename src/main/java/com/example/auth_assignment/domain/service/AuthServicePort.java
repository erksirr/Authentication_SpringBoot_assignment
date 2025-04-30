package com.example.auth_assignment.domain.service;

import com.example.auth_assignment.domain.model.User;

public interface AuthServicePort {
    String login(String username, String password);
    String register(String username, String password, String role); // <-- เพิ่ม
    User getCurrentUser();
}