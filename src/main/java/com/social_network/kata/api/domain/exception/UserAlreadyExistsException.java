package com.social_network.kata.api.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("User with username '" + username + "' already exists");
    }
}
