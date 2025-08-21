package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.DirectMessage;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.DirectMessageRepositoryPort;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectMessageService {
    private final DirectMessageRepositoryPort dmRepo;
    private final UserRepositoryPort userRepo;

    public DirectMessageService(DirectMessageRepositoryPort dmRepo, UserRepositoryPort userRepo) {
        this.dmRepo = dmRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public DirectMessage sendDirectMessage(String from, String to, String content) {
        User sender = userRepo.findByUsername(from).orElseGet(() -> userRepo.save(new User(from)));
        User recipient = userRepo.findByUsername(to).orElseGet(() -> userRepo.save(new User(to)));
        DirectMessage dm = new DirectMessage(sender, recipient, content, LocalDateTime.now());
        return dmRepo.save(dm);
    }

    @Transactional(readOnly = true)
    public List<DirectMessage> getInbox(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return dmRepo.findInbox(user).stream()
                .sorted(Comparator.comparing(DirectMessage::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DirectMessage> getConversation(String u1, String u2) {
        User a = userRepo.findByUsername(u1).orElseThrow();
        User a = userRepo.findByUsername(u1).orElseThrow(() -> new UserNotFoundException(u1));
        User b = userRepo.findByUsername(u2).orElseThrow(() -> new UserNotFoundException(u2));
        return dmRepo.findBetween(a, b).stream()
                .sorted(Comparator.comparing(DirectMessage::getTimestamp))
                .collect(Collectors.toList());
    }
}
