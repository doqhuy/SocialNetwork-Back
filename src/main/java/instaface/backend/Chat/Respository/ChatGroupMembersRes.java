package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.ChatGroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatGroupMembersRes extends JpaRepository<ChatGroupMembers, Long> {
    List<ChatGroupMembers> findAll();
    List<ChatGroupMembers> findAllByIdmember(String idmember);

    List<ChatGroupMembers> findAllBySttAndIdmember(long stt, String idmember);
    ChatGroupMembers findBySttAndIdmember(long stt, String idmem);

    List<ChatGroupMembers> findAllByStt(long stt);
    @Transactional
    void deleteBySttAndIdmember(long stt, String idmember);

    @Transactional
    void deleteByStt(long stt);
}
