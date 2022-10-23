package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import uz.jahongir.jahongirme.enums.ChatState;

public class NextChatStateContextHolder {

    private static final ThreadLocal<ChatState> value = new NamedThreadLocal<>("NextChatStateContext");

    public static void setValue(ChatState chatId) {
        NextChatStateContextHolder.value.set(chatId);
    }

    public static ChatState getValue() {
        return NextChatStateContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
