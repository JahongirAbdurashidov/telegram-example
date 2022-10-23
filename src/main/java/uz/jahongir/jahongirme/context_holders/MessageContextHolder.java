package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageContextHolder {

    private static final ThreadLocal<Message> value = new NamedThreadLocal<>("MessageContext");

    public static void setValue(Message chatId) {
        MessageContextHolder.value.set(chatId);
    }

    public static Message getValue() {
        return MessageContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
