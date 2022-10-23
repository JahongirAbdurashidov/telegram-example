package uz.jahongir.jahongirme.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jahongir.jahongirme.entities.ChatEntity;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.enums.Locale;
import uz.jahongir.jahongirme.repositories.ChatRepository;
import uz.jahongir.jahongirme.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    public ChatEntity setChat(String chatId) {
        ChatEntity entity = findByChatIdElseNew(chatId);
        entity.setTelegramChatId(chatId);
        repository.save(entity);
        return entity;
    }

    public void setLocale(String chatId, Locale locale) {
        ChatEntity entity = findByChatIdElseNew(chatId);
        entity.setTelegramChatId(chatId);
        entity.setLocale(locale);
        repository.save(entity);
    }

    public void setPhone(String chatId, String phone) {
        ChatEntity entity = findByChatIdElseNew(chatId);
        entity.setTelegramChatId(chatId);
        entity.setPhoneNumber(CommonUtil.getOnlyNumbers(phone));
        repository.save(entity);
    }

    public ChatEntity setChatState(String chatId, ChatState chatState) {
        ChatEntity entity = findByChatIdElseNew(chatId);
        entity.setTelegramChatId(chatId);
        entity.setChatState(chatState);
        repository.save(entity);
        return entity;
    }


    public Optional<ChatEntity> findByChatId(String chatId) {
        return repository.findFirstByTelegramChatId(chatId);
    }


    public ChatEntity setData(String chatId, Map<String, Object> data) {
        ChatEntity entity = findByChatIdElseNew(chatId);
        entity.setTelegramChatId(chatId);
        entity.setData(CommonUtil.toString(data));
        repository.save(entity);
        return entity;
    }

    public Map<String, Object> getData(String chatId) {
        ChatEntity entity = findByChatIdElseThrow(chatId);

        String data = entity.getData();

        if (data == null) return new HashMap<>();

        return CommonUtil.toMap(data);
    }


    public ChatState getState(String chatId) {
        Optional<ChatEntity> chatEntityOptional = findByChatId(chatId);

        if (chatEntityOptional.isPresent()) return chatEntityOptional.get().getChatState();

        else return ChatState.REGISTRATION;
    }

    public ChatEntity findByChatIdElseThrow(String chatId) {
        return findByChatId(chatId).orElseThrow();
    }

    public ChatEntity findByChatIdElseNew(String chatId) {
        return findByChatId(chatId).orElse(new ChatEntity());
    }
}
