package com.social_network.kata.api.presentation.rest;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.dto.PostMessageRequestDTO;
import com.social_network.kata.api.application.service.TimelineService;
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

    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO postMessage(@PathVariable String username, @RequestBody PostMessageRequestDTO request) {
        return timelineService.postMessage(username, request.getContent());
    }

    @GetMapping("/{username}")
    public List<MessageDTO> getTimeline(@PathVariable String username) {
        return timelineService.getTimeline(username);
    }
}