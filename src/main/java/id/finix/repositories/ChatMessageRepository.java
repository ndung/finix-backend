package id.finix.repositories;

import id.finix.domain.ChatMessage;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select c from ChatMessage c where c.topic in (:topic, '') order by c.created asc")
    List<ChatMessage> getChatMessages(@Param("topic") String topic, Pageable pageable);
}
