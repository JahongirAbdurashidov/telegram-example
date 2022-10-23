package uz.jahongir.jahongirme.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jahongir.jahongirme.entities.ChatEntity;
import uz.jahongir.jahongirme.entities.PayHistoryEntity;
import uz.jahongir.jahongirme.repositories.PayHistoryRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayHistoryService {

    private final PayHistoryRepository repository;
    private final ChatService chatService;


    public PayHistoryEntity save(String chatId, String title, Long sum) {

        ChatEntity chat = chatService.findByChatIdElseThrow(chatId);

        PayHistoryEntity entity = new PayHistoryEntity();
        entity.setChat(chat);
        entity.setTitle(title);
        entity.setSum(sum);
        entity.setCreatedDate(new Date(System.currentTimeMillis()));

        repository.save(entity);

        return entity;
    }

    public List<PayHistoryEntity> getListByChatId(Long chatId) {
        return repository.findByChat_Id(chatId);
    }
}
