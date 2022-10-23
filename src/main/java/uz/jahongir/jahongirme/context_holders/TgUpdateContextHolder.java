package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TgUpdateContextHolder {

    private static final ThreadLocal<Update> value = new NamedThreadLocal<>("TgUpdateContext");

    public static void setValue(Update chatId) {
        TgUpdateContextHolder.value.set(chatId);
    }

    public static Update getValue() {
        return TgUpdateContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
