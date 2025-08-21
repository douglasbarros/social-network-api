package com.social_network.kata.api.application.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.social_network.kata.api.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private List<MessageDTO> posts;
    private Set<UserDTO> following;

    public static UserDTO build(User user) {
        List<MessageDTO> posts = user.getPosts().stream().map(MessageDTO::build).toList();
        Set<UserDTO> following = user.getFollowing().stream().map(UserDTO::build).collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getUsername(), posts, following);
    }
}
