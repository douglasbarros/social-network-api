package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.application.dto.SimpleUserDTO;
import com.social_network.kata.api.domain.exception.UserAlreadyExistsException;
import com.social_network.kata.api.domain.model.User;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepositoryPort userRepo;

    public UserService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public SimpleUserDTO create(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }

        Optional<User> existingUser = userRepo.findByUsername(username);

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        User saved = userRepo.save(new User(username));

        return SimpleUserDTO.build(saved);
    }
}
