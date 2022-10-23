package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import uz.jahongir.jahongirme.enums.ChatState;

public class ChatStateContextHolder {

    private static final ThreadLocal<ChatState> value = new NamedThreadLocal<>("ChatStateContext");

    public static void setValue(ChatState chatId) {
        ChatStateContextHolder.value.set(chatId);
    }

    public static ChatState getValue() {
        return ChatStateContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
