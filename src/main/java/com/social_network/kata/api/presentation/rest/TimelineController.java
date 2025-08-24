package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.dto.PostMessageRequestDTO;
import com.social_network.kata.api.application.service.TimelineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeline")
public class TimelineController {
    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @Operation(summary = "Create a new post", description = "Creates a post and user, if user does not exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Content cannot be empty\", \"path\": \"/api/v1/timeline\" }")) })
    })
    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO postMessage(@PathVariable String username, @RequestBody PostMessageRequestDTO request) {
        return timelineService.postMessage(username, request.getContent());
    }

    @Operation(summary = "Returns the user's timeline", description = "Returns the user's timeline if user exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's timeline found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string\", \"path\": \"/api/v1/timeline/string\" }")) })
    })
    @GetMapping("/{username}")
    public List<MessageDTO> getTimeline(@PathVariable String username) {
        return timelineService.getTimeline(username);
    }
}