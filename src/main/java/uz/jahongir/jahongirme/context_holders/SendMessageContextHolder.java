package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import uz.jahongir.jahongirme.payloads.CustomSendMessage;

public class SendMessageContextHolder {

    private static final ThreadLocal<CustomSendMessage> value = new NamedThreadLocal<>("CustomSendMessageContext");

    public static void setValue(CustomSendMessage chatId) {
        SendMessageContextHolder.value.set(chatId);
    }

    public static CustomSendMessage getValue() {
        return SendMessageContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
