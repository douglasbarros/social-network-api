package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.domain.model.User;

@Service
public class FollowService {
    private final UserRepositoryPort userRepo;

    public FollowService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public void follow(String followerUsername, String followeeUsername) {
        if (followerUsername.equals(followeeUsername)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        User follower = userRepo.findByUsername(followerUsername)
                .orElseGet(() -> userRepo.save(new User(followerUsername)));
        User followee = userRepo.findByUsername(followeeUsername)
                .orElseGet(() -> userRepo.save(new User(followeeUsername)));

        follower.follow(followee);
        userRepo.save(follower);
    }
}
