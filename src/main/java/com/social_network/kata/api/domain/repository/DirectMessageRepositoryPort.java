package com.social_network.kata.api.domain.repository;

import java.util.List;
import com.social_network.kata.api.domain.model.DirectMessage;
import com.social_network.kata.api.domain.model.User;

public interface DirectMessageRepositoryPort {
    DirectMessage save(DirectMessage dm);

    List<DirectMessage> findInbox(User recipient);

    List<DirectMessage> findBetween(User u1, User u2);
}
