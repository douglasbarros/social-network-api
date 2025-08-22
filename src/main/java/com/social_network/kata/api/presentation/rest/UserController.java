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

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleUserDTO postMessage(@RequestBody CreateUserRequestDTO request) {
        return userService.create(request.getName());
    }
}
