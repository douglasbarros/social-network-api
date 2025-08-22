package com.social_network.kata.api.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "direct_messages")
public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User recipient;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public DirectMessage(User sender, User recipient, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
    }
}
