package com.social_network.kata.api.presentation.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.social_network.kata.api.application.dto.CreateUserRequestDTO;
import com.social_network.kata.api.application.dto.SimpleUserDTO;
import com.social_network.kata.api.application.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Creates a user if username does not exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "User already exists", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 409, \"error\": \"Bad Request\", \"message\": \"User with username 'string' already exists\", \"path\": \"/api/v1/user\" }")) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Username must not be null or empty\", \"path\": \"/api/v1/user\" }")) })
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleUserDTO create(@RequestBody CreateUserRequestDTO request) {
        return userService.create(request.getName());
    }
}
