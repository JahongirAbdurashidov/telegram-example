package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class ChatContextHolder {

    private static final ThreadLocal<Chat> value = new NamedThreadLocal<>("ChatContext");

    public static void setValue(Chat chatId) {
        ChatContextHolder.value.set(chatId);
    }

    public static void clear() {
        value.set(null);
    }

    public static Chat getValue() {
        return ChatContextHolder.value.get();
    }
}
