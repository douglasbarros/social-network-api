package com.social_network.kata.api.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.social_network.kata.api.application.service.DirectMessageService;
import com.social_network.kata.api.domain.model.DirectMessage;

import java.util.List;

@RestController
@RequestMapping("/dm")
public class DirectMessageController {

    private final DirectMessageService dmService;

    public DirectMessageController(DirectMessageService dmService) {
        this.dmService = dmService;
    }

    @PostMapping("/{from}/to/{to}")
    public DirectMessage sendMessage(@PathVariable String from,
            @PathVariable String to,
            @RequestBody String content) {
        return dmService.sendDirectMessage(from, to, content);
    }

    @GetMapping("/{username}")
    public List<DirectMessage> getInbox(@PathVariable String username) {
        return dmService.getInbox(username);
    }
}
