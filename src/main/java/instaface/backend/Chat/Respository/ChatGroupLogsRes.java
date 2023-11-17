package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.ChatGroup;
import instaface.backend.Chat.Models.ChatGroupLogs;
import instaface.backend.Chat.Models.ChatGroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatGroupLogsRes extends JpaRepository<ChatGroupLogs, Long> {
    List<ChatGroupLogs> findAllByStt(long stt);
    @Transactional
    void deleteByStt(long stt);
}