package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.DirectMessageDTO;
import com.social_network.kata.api.application.dto.PostMessageRequestDTO;
import com.social_network.kata.api.application.service.DirectMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dm")
public class DirectMessageController {

    private final DirectMessageService dmService;

    public DirectMessageController(DirectMessageService dmService) {
        this.dmService = dmService;
    }

    @Operation(summary = "Send a new direct message", description = "Sends a new direct message, if users exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DM sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Content cannot be empty\", \"path\": \"/api/v1/dm\" }")) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string1\", \"path\": \"/api/v1/dm/string1/to/string2\" }")) })
    })
    @PostMapping("/{from}/to/{to}")
    public DirectMessageDTO sendMessage(@PathVariable String from,
            @PathVariable String to,
            @RequestBody PostMessageRequestDTO request) {
        return dmService.sendDirectMessage(from, to, request.getContent());
    }

    @Operation(summary = "Returns the inbox", description = "Returns the user's inbox")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inbox was found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string\", \"path\": \"/api/v1/dm/string\" }")) })
    })
    @GetMapping("/{username}")
    public List<DirectMessageDTO> getInbox(@PathVariable String username) {
        return dmService.getInbox(username);
    }

    @Operation(summary = "Returns the conversation", description = "Returns the conversation between users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversation was found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"timestamp\": \"2025-08-24T12:08:26.699888\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User not found: string1\", \"path\": \"/api/v1/dm/between/string1/string2\" }")) })
    })
    @GetMapping("/between/{me}/{other}")
    public List<DirectMessageDTO> getConversation(@PathVariable String me,
            @PathVariable String other) {
        return dmService.getConversation(me, other);
    }
}
