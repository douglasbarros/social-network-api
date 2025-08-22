package com.social_network.kata.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.social_network.kata.api.application.dto.MessageDTO;
import com.social_network.kata.api.domain.exception.UserNotFoundException;
import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.repository.MessageRepositoryPort;
import com.social_network.kata.api.domain.repository.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class WallServiceTest {

    @Mock
    private MessageRepositoryPort messageRepo;

    @Mock
    private UserRepositoryPort userRepo;

    private WallService wallService;

    @BeforeEach
    void setUp() {
        wallService = new WallService(messageRepo, userRepo);
    }

    @Test
    void getWallShouldReturnMessagesFromUserAndFollowing() {
        User mainUser = new User("alice");
        User following1 = new User("bob");
        User following2 = new User("charlie");
        mainUser.follow(following1);
        mainUser.follow(following2);

        LocalDateTime now = LocalDateTime.now();
        Message message1 = new Message("Hello from Alice", mainUser, now, List.of(), List.of());
        Message message2 = new Message("Hello from Bob", following1, now.minusHours(1), List.of(), List.of());
        Message message3 = new Message("Hello from Charlie", following2, now.minusHours(2), List.of(), List.of());

        when(userRepo.findByUsername("alice")).thenReturn(Optional.of(mainUser));
        when(messageRepo.findByUsers(Mockito.anyList()))
                .thenReturn(Arrays.asList(message1, message2, message3));

        List<MessageDTO> wall = wallService.getWall("alice");

        assertEquals(3, wall.size());
        assertEquals("Hello from Alice", wall.get(0).getContent());
        assertEquals("Hello from Bob", wall.get(1).getContent());
        assertEquals("Hello from Charlie", wall.get(2).getContent());
    }

    @Test
    void getWallShouldReturnOnlyUserMessagesWhenNoFollowing() {
        User user = new User("doug");
        Message message = new Message("Hello from Doug", user, LocalDateTime.now(), List.of(), List.of());

        when(userRepo.findByUsername("doug")).thenReturn(Optional.of(user));
        when(messageRepo.findByUsers(Collections.singletonList(user)))
                .thenReturn(Collections.singletonList(message));

        List<MessageDTO> wall = wallService.getWall("doug");

        assertEquals(1, wall.size());
        assertEquals("Hello from Doug", wall.get(0).getContent());
    }

    @Test
    void getWallShouldThrowExceptionWhenUserNotFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> wallService.getWall("unknown"));
    }

    @Test
    void getWallShouldReturnMessagesInReverseOrder() {
        User user = new User("doug");
        LocalDateTime now = LocalDateTime.now();
        Message message1 = new Message("First", user, now.minusHours(2), List.of(), List.of());
        Message message2 = new Message("Second", user, now.minusHours(1), List.of(), List.of());
        Message message3 = new Message("Third", user, now, List.of(), List.of());

        when(userRepo.findByUsername("doug")).thenReturn(Optional.of(user));
        when(messageRepo.findByUsers(Collections.singletonList(user)))
                .thenReturn(Arrays.asList(message1, message2, message3));

        List<MessageDTO> wall = wallService.getWall("doug");

        assertEquals(3, wall.size());
        assertEquals("Third", wall.get(0).getContent());
        assertEquals("Second", wall.get(1).getContent());
        assertEquals("First", wall.get(2).getContent());
    }
}