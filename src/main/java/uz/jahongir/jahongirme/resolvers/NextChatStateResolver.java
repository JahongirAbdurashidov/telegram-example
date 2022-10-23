package uz.jahongir.jahongirme.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import uz.jahongir.jahongirme.context_holders.ChatContextHolder;
import uz.jahongir.jahongirme.context_holders.NextChatStateContextHolder;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.services.ChatService;

@Component
@RequiredArgsConstructor
public class NextChatStateResolver {

    private final ChatService chatService;

    public void resolve() {
        Chat chat = ChatContextHolder.getValue();
        String chatId = Long.toString(chat.getId());

        ChatState nextChatState = NextChatStateContextHolder.getValue();

        chatService.setChatState(chatId, nextChatState);
    }
}
