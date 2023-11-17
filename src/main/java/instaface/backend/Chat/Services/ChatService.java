package instaface.backend.Chat.Services;

import instaface.backend.Chat.Models.ChatLog;
import org.springframework.data.domain.Page;

import java.util.List;
public interface ChatService {
    List<ChatLog> getAllChat();
    void saveChat(ChatLog chatLog);
    ChatLog getChatById(long stt);
    void deleteCourseById(long stt);
    Page<ChatLog> findPaginated(int pageNum, int pageSize,
                                String sortField,
                                String sortDirection);
}
