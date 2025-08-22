package com.social_network.kata.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.MessageRepositoryPort;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class TimelineServiceTest {

    @Mock
    private MessageRepositoryPort messageRepo;

    @Mock
    private UserRepositoryPort userRepo;

    private TimelineService timelineService;

    @BeforeEach
    void setUp() {
        timelineService = new TimelineService(messageRepo, userRepo);
    }

    @Test
    void postMessageShouldCreateNewUserWhenUserDoesNotExist() {
        String username = "doug";
        String content = "Hello world";
        User newUser = new User(username);
        Message savedMessage = new Message(content, newUser, LocalDateTime.now(), List.of(), List.of());

        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(newUser);
        when(messageRepo.save(any(Message.class))).thenReturn(savedMessage);

        MessageDTO result = timelineService.postMessage(username, content);

        verify(userRepo).save(any(User.class));
        assertNotNull(result);
        assertEquals(content, result.getContent());
    }

    @Test
    void postMessageShouldUseExistingUser() {
        String username = "doug";
        String content = "Hello world";
        User existingUser = new User(username);
        Message savedMessage = new Message(content, existingUser, LocalDateTime.now(), List.of(), List.of());

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(existingUser));
        when(messageRepo.save(any(Message.class))).thenReturn(savedMessage);

        MessageDTO result = timelineService.postMessage(username, content);

        verify(userRepo, never()).save(any(User.class));
        assertNotNull(result);
        assertEquals(content, result.getContent());
    }

    @Test
    void postMessageShouldThrowExceptionWhenContentIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> timelineService.postMessage("doug", ""));
    }

    @Test
    void getTimelineShouldReturnUserMessages() {
        String username = "doug";
        User user = new User(username);
        Message message1 = new Message("Hello", user, LocalDateTime.now(), List.of(), List.of());
        Message message2 = new Message("World", user, LocalDateTime.now(), List.of(), List.of());

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(messageRepo.findByUser(user)).thenReturn(Arrays.asList(message1, message2));

        List<MessageDTO> timeline = timelineService.getTimeline(username);

        assertEquals(2, timeline.size());
        assertEquals("Hello", timeline.get(0).getContent());
        assertEquals("World", timeline.get(1).getContent());
    }

    @Test
    void getTimelineShouldThrowExceptionWhenUserNotFound() {
        String username = "unknown";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> timelineService.getTimeline(username));
    }

    @Test
    void getMentionsShouldReturnMessagesWithUserMentions() {
        String username = "doug";
        Message message1 = new Message("Hello @doug", new User("alice"), LocalDateTime.now(), List.of(username),
                List.of());
        Message message2 = new Message("Hi @doug", new User("bob"), LocalDateTime.now(), List.of(username), List.of());

        when(messageRepo.findByMentions(Collections.singletonList(username)))
                .thenReturn(Arrays.asList(message1, message2));

        List<MessageDTO> mentions = timelineService.getMentions(username);

        assertEquals(2, mentions.size());
        assertTrue(mentions.get(0).getContent().contains("@doug"));
        assertTrue(mentions.get(1).getContent().contains("@doug"));
    }
}