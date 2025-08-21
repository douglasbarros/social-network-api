package com.social_network.kata.api.infrastructure.persistence.jpa;

import com.social_network.kata.api.domain.model.Message;
import com.social_network.kata.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SpringDataMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByAuthorOrderByTimestampDesc(User author);

    @Query("select m from Message m where m.author in :authors")
    List<Message> findByAuthors(@Param("authors") List<User> authors);

    List<Message> findDistinctByMentionsIn(List<String> mentions);
}
