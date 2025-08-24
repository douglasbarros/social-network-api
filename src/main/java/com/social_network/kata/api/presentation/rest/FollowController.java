package com.social_network.kata.api.presentation.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_network.kata.api.application.service.FollowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @Operation(summary = "User follows another user", description = "A user follows another user if both exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User follows another user successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string1\", \"path\": \"/api/v1/follow/string1/to/string2\" }")) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"User cannot follow themselves\", \"path\": \"/api/v1/follow/string1/to/string1\" }")) })
    })
    @PostMapping("/{follower}/to/{followed}")
    public void follow(@PathVariable String follower, @PathVariable String followed) {
        followService.follow(follower, followed);
    }
}
