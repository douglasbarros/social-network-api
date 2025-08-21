package com.social_network.kata.api.application.dto;

import java.time.LocalDateTime;

import com.social_network.kata.api.domain.model.DirectMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DirectMessageDTO {
    private Long id;

    private SimpleUserDTO sender;

    private SimpleUserDTO recipient;

    private String content;

    private LocalDateTime timestamp;

    public static DirectMessageDTO build(DirectMessage directMessage) {
        SimpleUserDTO sender = new SimpleUserDTO(directMessage.getSender().getId(),
                directMessage.getSender().getUsername());
        SimpleUserDTO recipient = new SimpleUserDTO(directMessage.getRecipient().getId(),
                directMessage.getRecipient().getUsername());

        return new DirectMessageDTO(directMessage.getId(), sender, recipient, directMessage.getContent(),
                directMessage.getTimestamp());
    }
}