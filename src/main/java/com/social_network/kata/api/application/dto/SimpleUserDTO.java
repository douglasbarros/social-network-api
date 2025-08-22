package com.social_network.kata.api.application.dto;

import java.util.Objects;

import com.social_network.kata.api.domain.model.User;

public record SimpleUserDTO(Long id, String username) {
    public static SimpleUserDTO build(User user) {
        Objects.requireNonNull(user, "User must not be null");
        return new SimpleUserDTO(user.getId(), user.getUsername());
    }
}
