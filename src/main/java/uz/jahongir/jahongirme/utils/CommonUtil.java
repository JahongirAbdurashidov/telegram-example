package uz.jahongir.jahongirme.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.jahongir.jahongirme.bot.Bot;
import uz.jahongir.jahongirme.context_holders.*;
import uz.jahongir.jahongirme.entities.ChatEntity;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.enums.Locale;
import uz.jahongir.jahongirme.enums.MessageEnum;
import uz.jahongir.jahongirme.enums.TextIdentifier;
import uz.jahongir.jahongirme.payloads.CustomSendMessage;
import uz.jahongir.jahongirme.services.ChatService;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public static String getOnlyNumbers(String string) {
        return string
                .replaceAll("\\D+", "");
    }

    public static void clearAllContextHolders() {
        ChatContextHolder.clear();
        ChatStateContextHolder.clear();
        LocaleContextHolder.clear();
        SendMessageContextHolder.clear();
        TextContextHolder.clear();
        TgUpdateContextHolder.clear();
        MessageContextHolder.clear();
        NextChatStateContextHolder.clear();
    }

    public static void setUpAllContextHolders(Update update) {
        TgUpdateContextHolder.setValue(update);

        Message message = update.getMessage();
        MessageContextHolder.setValue(message);

        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());
        ChatContextHolder.setValue(chat);

        CustomSendMessage sendMessage = new CustomSendMessage(chatId);
        SendMessageContextHolder.setValue(sendMessage);


        ChatService chatService = ApplicationContextHolder.getBean(ChatService.class);

        Optional<ChatEntity> chatEntityOptional = chatService.findByChatId(chatId);

        TextIdentifier text;
        Locale locale;
        ChatState state;

        if (chatEntityOptional.isPresent()) {
            ChatEntity chatEntity = chatEntityOptional.get();

            // set up locale
            if (chatEntity.getLocale() != null) {
                locale = chatEntity.getLocale();
            } else locale = Locale.UZ;

            // set up chatState
            if (chatEntity.getChatState() != null) {
                state = chatEntity.getChatState();
            } else state = ChatState.REGISTRATION;

            // set up text
            if (message.hasText()) {
                text = TextIdentifier.getByMessage(message.getText());
            } else text = TextIdentifier.NOT_FOUND;


        } else {
            locale = Locale.UZ;
            state = ChatState.REGISTRATION;
            text = TextIdentifier.NOT_FOUND;
        }

        LocaleContextHolder.setValue(locale);
        ChatStateContextHolder.setValue(state);
        TextContextHolder.setValue(text);
        NextChatStateContextHolder.setValue(state);
    }

    public static void sendMessageToChat(String chatId, MessageEnum messageEnum) {
        sendMessageToChat(chatId, messageEnum.value());
    }

    public static void sendMessageToChat(String chatId, String message) {
        Bot bot = ApplicationContextHolder.getBean(Bot.class);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToChat(String message) {
        sendMessageToChat(ChatContextHolder.getValue().getId().toString(), message);
    }


    @SneakyThrows
    public static String toString(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static Map<String, Object> toMap(String obj) {
        return objectMapper.readValue(obj, new TypeReference<Map<String, Object>>() {
        });
    }

    public static boolean checkMobilePhone(String phone) {
        return Pattern.matches("[983][0-9]{8}", phone.trim());
    }

    public static boolean checkInternationalCommunicationCard(String phone) {
        return Pattern.matches("[983][0-9]{8}", phone.trim());
    }

    public static boolean checkCityPhone(String phone) {
        return Pattern.matches("[7][0-9]{8}", phone.trim());
    }

    public static boolean checkSum(String phone) {
        return Pattern.matches("[0-9]{4,8}", phone.trim());
    }
}
