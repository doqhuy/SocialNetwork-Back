package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.ChatDefine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDefineRes extends JpaRepository<ChatDefine, Long> {
    ChatDefine findByIdaAndIdb(String ida, String idb);
}
