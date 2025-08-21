package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social_network.kata.api.domain.repository.MessageRepositoryPort;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WallService {
    private final MessageRepositoryPort messageRepo;
    private final UserRepositoryPort userRepo;

    public WallService(MessageRepositoryPort messageRepo, UserRepositoryPort userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getWall(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<User> users = new ArrayList<>(user.getFollowing());
        users.add(user);
        return messageRepo.findByUsers(users).stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .map(message -> MessageDTO.build(message))
                .collect(Collectors.toList());
    }
}
