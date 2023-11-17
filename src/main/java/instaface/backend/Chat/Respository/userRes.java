package instaface.backend.Chat.Respository;

import instaface.backend.Chat.Models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRes extends JpaRepository<user, Long> {
    user findByUsernameAndPassword(String username, String password);
}
