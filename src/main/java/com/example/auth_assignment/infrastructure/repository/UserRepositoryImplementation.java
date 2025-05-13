package com.example.auth_assignment.infrastructure.repository;

import com.example.auth_assignment.adapter.out.repository.JpaUserRepository;
import com.example.auth_assignment.domain.model.User;
import com.example.auth_assignment.domain.repository.UserRepositoryPort;
import com.example.auth_assignment.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImplementation implements UserRepositoryPort {

    private JpaUserRepository jpaUserRepository;

    public UserRepositoryImplementation(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getPassword(),
                        entity.getRole()));
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        jpaUserRepository.save(entity);
    }
}
