package com.social_network.kata.api.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.application.service.WallService;

import java.util.List;

@RestController
@RequestMapping("/wall")
public class WallController {
    private final WallService wallService;

    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @GetMapping("/{username}")
    public List<MessageDTO> wall(@PathVariable String username) {
        return wallService.getWall(username);
    }
}
