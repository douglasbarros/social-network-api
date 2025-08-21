package com.social_network.kata.api.infrastructure.persistence.jpa;

import com.social_network.kata.api.domain.model.DirectMessage;
import com.social_network.kata.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SpringDataDirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    List<DirectMessage> findByRecipient(User recipient);

    @Query("select d from DirectMessage d where (d.sender = :a and d.recipient = :b) or (d.sender = :b and d.recipient = :a)")
    List<DirectMessage> findBetween(@Param("a") User a, @Param("b") User b);
}
