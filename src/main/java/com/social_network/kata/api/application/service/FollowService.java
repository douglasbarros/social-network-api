package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.User;

@Service
public class FollowService {
    private final UserRepositoryPort userRepo;

    public FollowService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public void follow(String followerUsername, String followedUsername) {
        if (followerUsername.equals(followedUsername)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        User follower = userRepo.findByUsername(followerUsername)
                .orElseThrow(() -> new UserNotFoundException(followerUsername));
        User followed = userRepo.findByUsername(followedUsername)
                .orElseThrow(() -> new UserNotFoundException(followedUsername));

        follower.follow(followed);
        userRepo.save(follower);
    }
}
