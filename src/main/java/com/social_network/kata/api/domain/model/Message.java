package com.social_network.kata.api.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 280)
    private String content;

    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ElementCollection
    @CollectionTable(name = "message_mentions", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "mention")
    private List<String> mentions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "message_links", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "link")
    private List<String> links = new ArrayList<>();

    public Message(String content, User author, LocalDateTime timestamp, List<String> mentions, List<String> links) {
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
        this.mentions = mentions != null ? mentions : new ArrayList<>();
        this.links = links != null ? links : new ArrayList<>();
    }
}
