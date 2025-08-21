package com.social_network.kata.api.domain.repository;

import com.social_network.kata.api.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);

    User save(User user);

    boolean existsByUsername(String username);
}
