package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.service.TimelineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mentions")
public class MentionsController {
    private final TimelineService timelineService;

    public MentionsController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @Operation(summary = "Get mentions", description = "Returns mentions by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mention list found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Username cannot be empty\", \"path\": \"/api/v1/mentions\" }")) })
    })
    @GetMapping("/{username}")
    public List<MessageDTO> getMentions(@PathVariable String username) {
        return timelineService.getMentions(username);
    }
}
