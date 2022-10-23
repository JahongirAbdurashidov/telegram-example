package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import uz.jahongir.jahongirme.enums.TextIdentifier;

public class TextContextHolder {

    private static final ThreadLocal<TextIdentifier> value = new NamedThreadLocal<>("TextContext");

    public static void setValue(TextIdentifier chatId) {
        TextContextHolder.value.set(chatId);
    }

    public static TextIdentifier getValue() {
        return TextContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
