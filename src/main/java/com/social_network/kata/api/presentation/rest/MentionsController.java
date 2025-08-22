package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.service.TimelineService;

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

    @GetMapping("/{username}")
    public List<MessageDTO> getMentions(@PathVariable String username) {
        return timelineService.getMentions(username);
    }
}
