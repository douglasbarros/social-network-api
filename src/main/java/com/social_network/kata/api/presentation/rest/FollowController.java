package com.social_network.kata.api.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.social_network.kata.api.application.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{follower}/to/{followee}")
    public void follow(@PathVariable String follower, @PathVariable String followee) {
        followService.follow(follower, followee);
    }
}
