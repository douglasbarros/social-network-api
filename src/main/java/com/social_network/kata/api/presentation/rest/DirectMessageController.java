package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.DirectMessageDTO;
import com.social_network.kata.api.application.dto.PostMessageRequestDTO;
import com.social_network.kata.api.application.service.DirectMessageService;

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

    @PostMapping("/{from}/to/{to}")
    public DirectMessageDTO sendMessage(@PathVariable String from,
            @PathVariable String to,
            @RequestBody PostMessageRequestDTO request) {
        return dmService.sendDirectMessage(from, to, request.getContent());
    }

    @GetMapping("/{username}")
    public List<DirectMessageDTO> getInbox(@PathVariable String username) {
        return dmService.getInbox(username);
    }

    @GetMapping("/between/{me}/{other}")
    public List<DirectMessageDTO> getConversation(@PathVariable String me,
            @PathVariable String other) {
        return dmService.getConversation(me, other);
    }
}
