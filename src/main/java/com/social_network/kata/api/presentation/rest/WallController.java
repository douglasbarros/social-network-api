package com.social_network.kata.api.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.service.WallService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/wall")
public class WallController {
    private final WallService wallService;

    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @Operation(summary = "Returns the user's wall", description = "Returns the user's wall if user exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's wall found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string\", \"path\": \"/api/v1/wall/string\" }")) })
    })
    @GetMapping("/{username}")
    public List<MessageDTO> wall(@PathVariable String username) {
        return wallService.getWall(username);
    }
}
