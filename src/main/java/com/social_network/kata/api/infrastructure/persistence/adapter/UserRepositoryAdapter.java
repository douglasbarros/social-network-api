package com.social_network.kata.api.infrastructure.persistence.adapter;

import com.social_network.kata.api.domain.model.User;
import org.springframework.stereotype.Component;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.infrastructure.persistence.jpa.SpringDataUserRepository;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository repo;

    public UserRepositoryAdapter(SpringDataUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }
}
