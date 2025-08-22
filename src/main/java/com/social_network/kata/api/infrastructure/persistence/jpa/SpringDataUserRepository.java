package com.social_network.kata.api.infrastructure.persistence.jpa;

import com.social_network.kata.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
