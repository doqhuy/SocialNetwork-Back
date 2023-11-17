package instaface.backend.Chat.Respository;

import instaface.backend.domain.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface _HuyFriendRes extends JpaRepository<Relationship, Long> {
    List<Relationship> findAllByUserOneId(String id);
    List<Relationship> findAllByUserTwoId(String id);
}
