package uz.jahongir.jahongirme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jahongir.jahongirme.entities.PayHistoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayHistoryRepository extends JpaRepository<PayHistoryEntity, Long> {
    List<PayHistoryEntity> findByChat_Id(Long chat_id);
}
