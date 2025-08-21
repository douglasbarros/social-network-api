package com.social_network.kata.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;
import com.social_network.kata.api.domain.repository.MessageRepositoryPort;
import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TimelineService {
    private final MessageRepositoryPort messageRepo;
    private final UserRepositoryPort userRepo;

    private static final Pattern MENTION = Pattern.compile("@(\\w+)");
    private static final Pattern LINK = Pattern.compile("https?://\\S+");

    public TimelineService(MessageRepositoryPort messageRepo, UserRepositoryPort userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public MessageDTO postMessage(String username, String content) {
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Content cannot be empty");
        User author = userRepo.findByUsername(username).orElseGet(() -> userRepo.save(new User(username)));
        List<String> mentions = extract(MENTION, content);
        List<String> links = extract(LINK, content);
        Message msg = new Message(content, author, LocalDateTime.now(), mentions, links);
        return MessageDTO.build(messageRepo.save(msg));
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getTimeline(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return messageRepo.findByUser(user).stream()
                .map(message -> MessageDTO.build(message))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getMentions(String username) {
        return messageRepo.findByMentions(Collections.singletonList(username)).stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .map(message -> MessageDTO.build(message))
                .collect(Collectors.toList());
    }

    private static List<String> extract(Pattern p, String content) {
        List<String> out = new ArrayList<>();
        Matcher m = p.matcher(content);
        while (m.find())
            out.add(m.group().startsWith("@") ? m.group(1) : m.group());
        return out;
    }
}
