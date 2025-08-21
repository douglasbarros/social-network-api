package com.social_network.kata.api.infrastructure.persistence.adapter;

import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;
import org.springframework.stereotype.Component;
import com.social_network.kata.api.domain.repository.MessageRepositoryPort;
import com.social_network.kata.api.infrastructure.persistence.jpa.SpringDataMessageRepository;

import java.util.List;

@Component
public class MessageRepositoryAdapter implements MessageRepositoryPort {
    private final SpringDataMessageRepository repo;

    public MessageRepositoryAdapter(SpringDataMessageRepository repo) {
        this.repo = repo;
    }

    @Override
    public Message save(Message message) {
        return repo.save(message);
    }

    @Override
    public List<Message> findByUser(User user) {
        return repo.findByAuthorOrderByTimestampDesc(user);
    }

    @Override
    public List<Message> findByUsers(List<User> users) {
        return repo.findByAuthors(users);
    }

    @Override
    public List<Message> findByMentions(List<String> mentions) {
        return repo.findDistinctByMentionsIn(mentions);
    }
}
