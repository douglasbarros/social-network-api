package com.social_network.kata.api.application.dto;

import java.util.List;

import com.social_network.kata.api.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private List<MessageDTO> posts;

    public static UserDTO build(User user) {
        List<MessageDTO> posts = user.getPosts().stream().map(MessageDTO::build).toList();
        return new UserDTO(user.getId(), user.getUsername(), posts);
    }
}
