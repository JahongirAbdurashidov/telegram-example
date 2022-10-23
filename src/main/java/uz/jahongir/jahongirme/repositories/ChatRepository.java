package uz.jahongir.jahongirme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jahongir.jahongirme.entities.ChatEntity;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findFirstByTelegramChatId(String telegramChatId);
}
