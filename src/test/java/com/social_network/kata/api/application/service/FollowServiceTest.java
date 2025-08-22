package com.social_network.kata.api.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private UserRepositoryPort userRepo;

    private FollowService followService;

    @BeforeEach
    void setUp() {
        followService = new FollowService(userRepo);
    }

    @Test
    void followShouldUseExistingUsers() {
        String follower = "alice";
        String followed = "bob";

        User followerUser = new User(follower);
        User followedUser = new User(followed);

        when(userRepo.findByUsername(follower)).thenReturn(Optional.of(followerUser));
        when(userRepo.findByUsername(followed)).thenReturn(Optional.of(followedUser));

        followService.follow(follower, followed);

        verify(userRepo, times(1)).save(followerUser);
    }

    @Test
    void shouldThrowExceptionWhenUserTriesToFollowNonExistentUser() {
        String follower = "alice";
        String followee = "bob";

        User followerUser = new User(follower);

        when(userRepo.findByUsername(follower)).thenReturn(Optional.of(followerUser));
        when(userRepo.findByUsername(followee)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> followService.follow(follower, followee));

        verify(userRepo, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserTriesToFollowThemself() {
        String username = "charlie";

        assertThrows(IllegalArgumentException.class,
                () -> followService.follow(username, username));

        verify(userRepo, never()).save(any());
    }
}
