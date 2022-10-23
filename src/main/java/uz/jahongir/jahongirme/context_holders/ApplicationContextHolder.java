package uz.jahongir.jahongirme.context_holders;

import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationContextHolder {

    private static ConfigurableApplicationContext value;

    public static void setValue(ConfigurableApplicationContext chatId) {
        ApplicationContextHolder.value = chatId;
    }

    public static ConfigurableApplicationContext getValue() {
        return ApplicationContextHolder.value;
    }

    public static <T> T getBean(Class<T> tClass) {
        return value.getBean(tClass);
    }
}
