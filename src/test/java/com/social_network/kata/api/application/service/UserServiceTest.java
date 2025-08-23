package com.social_network.kata.api.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.social_network.kata.api.application.dto.SimpleUserDTO;
import com.social_network.kata.api.domain.exception.UserAlreadyExistsException;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepo;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepo);
    }

    @Test
    void createShouldSaveNewUserWhenUsernameDoesNotExist() {
        String username = "doug";
        User user = new User(username);
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(user);

        SimpleUserDTO result = userService.create(username);

        assertNotNull(result);
        assertEquals(username, result.username());
        verify(userRepo).findByUsername(username);
        verify(userRepo).save(any(User.class));
    }

    @Test
    void createShouldThrowExceptionWhenUsernameExists() {
        String username = "doug";
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(new User(username)));

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.create(username));
        assertEquals("User with username '" + username + "' already exists", exception.getMessage());
        verify(userRepo).findByUsername(username);
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void createShouldThrowExceptionWhenUsernameIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(null));
        assertEquals("Username must not be null or empty", exception.getMessage());
        verify(userRepo, never()).findByUsername(any());
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void createShouldThrowExceptionWhenUsernameIsEmpty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.create("  "));
        assertEquals("Username must not be null or empty", exception.getMessage());
        verify(userRepo, never()).findByUsername(any());
        verify(userRepo, never()).save(any(User.class));
    }
}
