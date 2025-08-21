package com.social_network.kata.api.infrastructure.persistence.adapter;

import com.social_network.kata.api.domain.model.DirectMessage;
import com.social_network.kata.api.domain.model.User;
import org.springframework.stereotype.Component;
import com.social_network.kata.api.domain.repository.DirectMessageRepositoryPort;
import com.social_network.kata.api.infrastructure.persistence.jpa.SpringDataDirectMessageRepository;

import java.util.List;

@Component
public class DirectMessageRepositoryAdapter implements DirectMessageRepositoryPort {
    private final SpringDataDirectMessageRepository repo;

    public DirectMessageRepositoryAdapter(SpringDataDirectMessageRepository repo) {
        this.repo = repo;
    }

    @Override
    public DirectMessage save(DirectMessage dm) {
        return repo.save(dm);
    }

    @Override
    public List<DirectMessage> findInbox(User recipient) {
        return repo.findByRecipient(recipient);
    }

    @Override
    public List<DirectMessage> findBetween(User u1, User u2) {
        return repo.findBetween(u1, u2);
    }
}
