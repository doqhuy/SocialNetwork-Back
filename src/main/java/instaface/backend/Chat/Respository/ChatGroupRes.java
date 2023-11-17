package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.ChatDefine;
import instaface.backend.Chat.Models.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ChatGroupRes extends JpaRepository<ChatGroup, Long> {
    List<ChatGroup> findAll();
    ChatGroup findByStt(long stt);

    @Transactional
    void deleteByStt(long stt);
}