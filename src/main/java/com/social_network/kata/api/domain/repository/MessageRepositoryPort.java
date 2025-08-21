package com.social_network.kata.api.domain.repository;

import com.social_network.kata.api.domain.model.User;
import com.social_network.kata.api.domain.model.Message;
import java.util.List;

public interface MessageRepositoryPort {
    Message save(Message message);

    List<Message> findByUser(User user);

    List<Message> findByUsers(List<User> users);

    List<Message> findByMentions(List<String> mentions);
}
