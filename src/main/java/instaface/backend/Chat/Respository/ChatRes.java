package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRes extends JpaRepository<ChatLog, Long> {
//    Optional<ChatLog> findBy(String url);
List<ChatLog> findByStt(long stt);
}
