package com.social_network.kata.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.social_network.kata.api.application.dto.DirectMessageDTO;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.DirectMessage;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.DirectMessageRepositoryPort;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DirectMessageServiceTest {

    @Mock
    private DirectMessageRepositoryPort dmRepo;

    @Mock
    private UserRepositoryPort userRepo;

    private DirectMessageService dmService;

    @BeforeEach
    void setUp() {
        dmService = new DirectMessageService(dmRepo, userRepo);
    }

    @Test
    void sendDirectMessageShouldCreateNewDirectMessage() {
        String sender = "alice";
        String recipient = "bob";
        String content = "Hello";

        User senderUser = new User(sender);
        User recipientUser = new User(recipient);
        DirectMessage dm = new DirectMessage(senderUser, recipientUser, content, LocalDateTime.now());

        when(userRepo.findByUsername(sender)).thenReturn(Optional.of(senderUser));
        when(userRepo.findByUsername(recipient)).thenReturn(Optional.of(recipientUser));
        when(dmRepo.save(any(DirectMessage.class))).thenReturn(dm);

        DirectMessageDTO result = dmService.sendDirectMessage(sender, recipient, content);

        assertEquals(content, result.getContent());
    }

    @Test
    void sendDirectMessageShouldThrowWhenUserNotFound() {
        String sender = "alice";
        String recipient = "bob";
        String content = "Hello";

        assertThrows(UserNotFoundException.class,
                () -> dmService.sendDirectMessage(sender, recipient, content));
    }

    @Test
    void getInboxShouldReturnMessagesInOrder() {
        String username = "alice";
        User user = new User(username);
        DirectMessage dm1 = new DirectMessage(new User("charlie"), user, "Hi", LocalDateTime.now());
        DirectMessage dm2 = new DirectMessage(new User("bob"), user, "Hello", LocalDateTime.now().plusHours(1));

        when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(dmRepo.findInbox(user)).thenReturn(Arrays.asList(dm1, dm2));

        List<DirectMessageDTO> result = dmService.getInbox(username);

        assertEquals(2, result.size());
        assertEquals(dm2.getContent(), result.getFirst().getContent());
    }

    @Test
    void getInboxShouldThrowWhenUserNotFound() {
        String username = "alice";

        assertThrows(UserNotFoundException.class,
                () -> dmService.getInbox(username));
    }

    @Test
    void getConversationShouldReturnConversation() {
        String senderName = "alice";
        String recipientName = "charlie";

        User sender = new User(senderName);
        User recipient = new User(recipientName);

        DirectMessage dm1 = new DirectMessage(sender, recipient, "Hi", LocalDateTime.now());
        DirectMessage dm2 = new DirectMessage(recipient, sender, "Hello", LocalDateTime.now().plusHours(1));

        when(userRepo.findByUsername(senderName)).thenReturn(Optional.of(sender));
        when(userRepo.findByUsername(recipientName)).thenReturn(Optional.of(recipient));
        when(dmRepo.findBetween(any(User.class), any(User.class))).thenReturn(Arrays.asList(dm1, dm2));

        List<DirectMessageDTO> result = dmService.getConversation(senderName, recipientName);

        assertEquals(2, result.size());
        assertEquals(dm1.getContent(), result.getFirst().getContent());
    }

    @Test
    void getConversationShouldThrowWhenUserNotFound() {
        String user1 = "alice";
        String user2 = "charlie";

        when(userRepo.findByUsername(user1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> dmService.getConversation(user1, user2));
    }
}
