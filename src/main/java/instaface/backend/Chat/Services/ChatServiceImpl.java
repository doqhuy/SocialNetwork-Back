package instaface.backend.Chat.Services;

import instaface.backend.Chat.Models.ChatLog;
import instaface.backend.Chat.Respository.ChatRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRes chatRes;

    @Override
    public List<ChatLog> getAllChat() {
        return chatRes.findAll();
    }



    @Override
    public void saveChat(ChatLog chatLog) {
        this.chatRes.save(chatLog);
    }

    @Override
    public ChatLog getChatById(long stt) {
        Optional<ChatLog> optionalCourse = chatRes.findById(stt);
        ChatLog chatLog = null;
        if (optionalCourse.isPresent()) {
            chatLog = optionalCourse.get();
        } else {
            throw new RuntimeException("Course not found for id : " + stt);
        }
        return chatLog;
    }

    @Override
    public void deleteCourseById(long id) {
        this.chatRes.deleteById(id);
    }

    @Override
    public Page<ChatLog> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return this.chatRes.findAll(pageable);
    }

}
