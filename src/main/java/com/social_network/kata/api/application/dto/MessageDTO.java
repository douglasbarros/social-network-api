package com.social_network.kata.api.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.social_network.kata.api.domain.model.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String content;
    private SimpleUserDTO author;
    private LocalDateTime timestamp;
    private List<String> mentions;
    private List<String> links;

    public static MessageDTO build(Message message) {
        SimpleUserDTO author = SimpleUserDTO.build(message.getAuthor());
        return new MessageDTO(message.getId(), message.getContent(), author,
                message.getTimestamp(), message.getMentions(), message.getLinks());
    }
}
