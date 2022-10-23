package uz.jahongir.jahongirme.context_holders;

import org.springframework.core.NamedThreadLocal;
import uz.jahongir.jahongirme.enums.Locale;

public class LocaleContextHolder {

    private static final ThreadLocal<Locale> value = new NamedThreadLocal<>("LocaleContext");

    public static void setValue(Locale chatId) {
        LocaleContextHolder.value.set(chatId);
    }

    public static Locale getValue() {
        return LocaleContextHolder.value.get();
    }

    public static void clear() {
        value.set(null);
    }
}
