package instaface.backend.Chat.Respository;

import instaface.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface _HuyUserRes extends JpaRepository<User, Long> {
    List<User> findAll();

    List<User> findAllById(List<String> uniqueIds);

    User findById(String id);
}
