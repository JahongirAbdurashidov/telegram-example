package uz.jahongir.jahongirme.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.*;
import uz.jahongir.jahongirme.context_holders.*;
import uz.jahongir.jahongirme.entities.ChatEntity;
import uz.jahongir.jahongirme.entities.PayHistoryEntity;
import uz.jahongir.jahongirme.enums.*;
import uz.jahongir.jahongirme.payloads.CustomSendMessage;
import uz.jahongir.jahongirme.services.ChatService;
import uz.jahongir.jahongirme.services.PayHistoryService;
import uz.jahongir.jahongirme.utils.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

import static uz.jahongir.jahongirme.enums.ChatState.*;
import static uz.jahongir.jahongirme.utils.CommonUtil.sendMessageToChat;

@Component
@RequiredArgsConstructor
public class Resolver {

    private final ChatService chatService;
    private final PayHistoryService payHistoryService;

    public void resolve() {

        Chat chat = ChatContextHolder.getValue();
        String chatId = Long.toString(chat.getId());

        ChatEntity chatEntity;

        Optional<ChatEntity> chatEntityOptional = chatService.findByChatId(chatId);

        if (chatEntityOptional.isEmpty()) {
            chatService.setChat(chatId);
            chatEntity = chatService.setChatState(chatId, ChatState.REGISTRATION);
        } else chatEntity = chatEntityOptional.get();

        ChatState chatState = chatEntity.getChatState();

        switch (chatState) {
            case REGISTRATION: {
                resolveRegistrationState();
                NextChatStateContextHolder.setValue(ChatState.LOCALE);
                break;
            }
            case LOCALE: {
                resolveLocaleState();
                break;
            }
            case SHARE_CONTACT: {
                resolveShareContactState();
                break;
            }
            case MAIN_MENU: {
                resolveMainMenuState();
                break;
            }
            case PAYMENT: {
                resolvePaymentsState();
                break;
            }

            case HOME_PHONE_MENU: {
                resolveHomePhoneMenuState();
                break;
            }

            case REGION_PHONE: {
                resolveRegionPhoneState();
                break;
            }
            case SUM_REGION_PHONE: {
                resolveSumRegionPhoneState();
                break;
            }

            case CITY_PHONE: {
                resolveCityPhoneState();
                break;
            }
            case SUM_CITY_PHONE: {
                resolveSumCityPhoneState();
                break;
            }


            case ADD_PAYMENT_MOBILE_PHONE: {
                resolveAddPaymentMobilePhoneState();
                break;
            }
            case SUM_ADD_PAYMENT_MOBILE_PHONE: {
                resolveSumAddPaymentMobilePhoneState();
                break;
            }
            case SETTINGS: {
                resolveSettingsState();
                break;
            }
            case CHANGE_LOCALE: {
                resolveChangeLocaleState();
                break;
            }
            case CHANGE_PHONE: {
                resolveChangePhoneState();
                break;
            }
            default: {
                resolveDefaultState();
            }
        }

    }

    public void resolveRegistrationState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();
        sendMessage.setText(MessageEnum.PLEASE_SELECT_LOCALE);
    }

    public void resolveLocaleState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        Locale locale;

        switch (textIdentifier) {
            case BTN_LOCALE_EN: {
                locale = Locale.EN;
                break;
            }
            case BTN_LOCALE_UZ: {
                locale = Locale.UZ;
                break;
            }
            default: {
                resolveRegistrationState();
                return;
            }
        }

        Chat chat = ChatContextHolder.getValue();
        String chatId = Long.toString(chat.getId());

        chatService.setLocale(chatId, locale);

        LocaleContextHolder.setValue(locale);

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();
        sendMessage.setText(MessageEnum.PLEASE_SEND_US_YOUR_PHONE);
        NextChatStateContextHolder.setValue(ChatState.SHARE_CONTACT);
    }

    public void resolveDefaultState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();
        sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
    }

    public void resolveShareContactState() {
        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (!message.hasContact()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        User from = message.getFrom();
        Contact contact = message.getContact();

        if (!from.getId().equals(contact.getUserId())) {
            sendMessage.setText(MessageEnum.PLEASE_SEND_US_CORRECT_YOUR_PHONE);
            return;
        }

        chatService.setPhone(chatId, contact.getPhoneNumber());

        sendMessage.setText(MessageEnum.MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.MAIN_MENU);

    }

    public void resolveMainMenuState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        switch (textIdentifier) {
            case BTN_PAYMENT: {
                sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.PAYMENT);
                break;
            }
            case BTN_SETTINGS: {
                sendMessage.setText(MessageEnum.SETTINGS_MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.SETTINGS);
                break;
            }
            case BTN_PAY_HISTORY: {
                sendPayHistory();
                break;
            }
            case BTN_BACK: {
                sendMessage.setText(MessageEnum.MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.MAIN_MENU);
                break;
            }
            default: {
                sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            }
        }

    }

    public void resolvePaymentsState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        switch (textIdentifier) {
            case BTN_PAYMENT_MOBILE_PHONE: {
                sendMessageToChat(sendMessage.getChatId(), MessageEnum.PLEASE_SEND_US_YOUR_PAYMENT_MOBILE_PHONE);
                sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_MOBILE_PHONE);
                NextChatStateContextHolder.setValue(ChatState.ADD_PAYMENT_MOBILE_PHONE);
                break;
            }
            case BTN_PAYMENT_HOME_MENU: {
                sendMessage.setText(MessageEnum.HOME_PHONE_MENU);
                NextChatStateContextHolder.setValue(ChatState.HOME_PHONE_MENU);
                break;
            }
            case BTN_BACK: {
                sendMessage.setText(MessageEnum.MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.MAIN_MENU);
                break;
            }
            default: {
                sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            }
        }
    }

    public void resolveHomePhoneMenuState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        switch (textIdentifier) {
            case BTN_PAYMENT_HOME_MENU_REGION_PHONE: {
                sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_REGION_PHONE);
                NextChatStateContextHolder.setValue(REGION_PHONE);
                break;
            }
            case BTN_PAYMENT_HOME_MENU_CITY_PHONE:
                sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_CITY_PHONE);
                NextChatStateContextHolder.setValue(CITY_PHONE);
                break;
            case BTN_BACK: {
                sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.PAYMENT);
                break;
            }
            default: {
                sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            }
        }
    }

    public void resolveRegionPhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_BACK) {
            sendMessage.setText(MessageEnum.HOME_PHONE_MENU);
            NextChatStateContextHolder.setValue(ChatState.HOME_PHONE_MENU);
            return;
        }

        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String phone = message.getText();

        if (!CommonUtil.checkCityPhone(phone)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);

        chatService.setData(chatId, data);

        sendMessage.setText(MessageEnum.SEND_SUM);
        NextChatStateContextHolder.setValue(ChatState.SUM_REGION_PHONE);
    }

    public void resolveCityPhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_BACK) {
            sendMessage.setText(MessageEnum.HOME_PHONE_MENU);
            NextChatStateContextHolder.setValue(ChatState.HOME_PHONE_MENU);
            return;
        }

        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String phone = message.getText();

        if (!CommonUtil.checkCityPhone(phone)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);

        chatService.setData(chatId, data);

        sendMessage.setText(MessageEnum.SEND_SUM);
        NextChatStateContextHolder.setValue(ChatState.SUM_CITY_PHONE);
    }

    public void resolveSumRegionPhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_CANCEL) {
            sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_REGION_PHONE);
            NextChatStateContextHolder.setValue(REGION_PHONE);
            return;

        }
        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String sumAsString = message.getText();

        if (!CommonUtil.checkSum(sumAsString)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        Long sum = Long.valueOf(sumAsString);

        final String title = ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.value();

        payHistoryService.save(chatId, title, sum);

        sendMessageToChat(chatId, MessageEnum.SUCCESSFULLY_PAYMENT);

        sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.PAYMENT);
    }

    public void resolveSumCityPhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_CANCEL) {
            sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_CITY_PHONE);
            NextChatStateContextHolder.setValue(CITY_PHONE);
            return;

        }
        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String sumAsString = message.getText();

        if (!CommonUtil.checkSum(sumAsString)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        Long sum = Long.valueOf(sumAsString);

        final String title = ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.value();

        payHistoryService.save(chatId, title, sum);

        sendMessageToChat(chatId, MessageEnum.SUCCESSFULLY_PAYMENT);

        sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.PAYMENT);
    }

    public void resolveAddPaymentMobilePhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_BACK) {
            sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
            NextChatStateContextHolder.setValue(ChatState.PAYMENT);
            return;
        }
        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String phone = message.getText();

        if (!CommonUtil.checkMobilePhone(phone)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);

        chatService.setData(chatId, data);

        sendMessage.setText(MessageEnum.SEND_SUM);
        NextChatStateContextHolder.setValue(ChatState.SUM_ADD_PAYMENT_MOBILE_PHONE);
    }

    public void resolveSumAddPaymentMobilePhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_CANCEL) {
            sendMessage.setText(MessageEnum.PAYMENT_EXAMPLE_WITH_MOBILE_PHONE);
            NextChatStateContextHolder.setValue(ADD_PAYMENT_MOBILE_PHONE);
            return;

        }
        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        if (!message.hasText()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        String sumAsString = message.getText();

        if (!CommonUtil.checkSum(sumAsString)) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        Long sum = Long.valueOf(sumAsString);

        final String title = ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.value();

        payHistoryService.save(chatId, title, sum);

        sendMessageToChat(chatId, MessageEnum.SUCCESSFULLY_PAYMENT);

        sendMessage.setText(MessageEnum.PAYMENT_MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.PAYMENT);
    }

    public void resolveSettingsState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        switch (textIdentifier) {
            case BTN_CHANGE_LOCALE: {
                sendMessage.setText(MessageEnum.PLEASE_SELECT_LOCALE);
                NextChatStateContextHolder.setValue(ChatState.CHANGE_LOCALE);
                break;
            }
            case BTN_CHANGE_PHONE: {
                sendMessage.setText(MessageEnum.PLEASE_SEND_US_YOUR_PHONE);
                NextChatStateContextHolder.setValue(ChatState.CHANGE_PHONE);
                break;
            }
            case BTN_BACK: {
                sendMessage.setText(MessageEnum.MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.MAIN_MENU);
                break;
            }
            default: {
                sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            }
        }

    }

    public void resolveChangeLocaleState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        Locale locale;

        switch (textIdentifier) {
            case BTN_LOCALE_EN: {
                locale = Locale.EN;
                break;
            }
            case BTN_LOCALE_UZ: {
                locale = Locale.UZ;
                break;
            }
            case BTN_BACK: {
                sendMessage.setText(MessageEnum.SETTINGS_MAIN_MENU);
                NextChatStateContextHolder.setValue(ChatState.SETTINGS);
                return;
            }
            default: {
                sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
                return;
            }
        }

        Chat chat = ChatContextHolder.getValue();
        String chatId = Long.toString(chat.getId());

        chatService.setLocale(chatId, locale);

        LocaleContextHolder.setValue(locale);

        sendMessage.setText(MessageEnum.SETTINGS_MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.SETTINGS);
    }

    public void resolveChangePhoneState() {
        TextIdentifier textIdentifier = TextContextHolder.getValue();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        if (textIdentifier == TextIdentifier.BTN_BACK) {
            sendMessage.setText(MessageEnum.SETTINGS_MAIN_MENU);
            NextChatStateContextHolder.setValue(ChatState.SETTINGS);
            return;
        }

        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());
        if (!message.hasContact()) {
            sendMessage.setText(MessageEnum.INCORRECT_MESSAGE);
            return;
        }

        User from = message.getFrom();
        Contact contact = message.getContact();

        if (!from.getId().equals(contact.getUserId())) {
            sendMessage.setText(MessageEnum.PLEASE_SEND_US_CORRECT_YOUR_PHONE);
            return;
        }

        chatService.setPhone(chatId, contact.getPhoneNumber());

        sendMessageToChat(chatId, MessageEnum.CONFIRM_PHONE);

        sendMessage.setText(MessageEnum.SETTINGS_MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.SETTINGS);
    }

    public void sendPayHistory() {

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        Update update = TgUpdateContextHolder.getValue();
        Message message = update.getMessage();
        Chat chat = message.getChat();
        String chatId = Long.toString(chat.getId());

        ChatEntity chatEntity = chatService.findByChatIdElseThrow(chatId);

        sendMessageToChat(chatId, MessageEnum.PAY_HISTORY);
        for (PayHistoryEntity entity : payHistoryService.getListByChatId(chatEntity.getId())) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            final String messageText =
                    simpleDateFormat.format(entity.getCreatedDate())
                            + "\n"
                            + ButtonMessageEnum.getByMessage(entity.getTitle())
                            + "\n"
                            + entity.getSum() + " so`m";

            sendMessageToChat(chatId, messageText);

        }


        sendMessage.setText(MessageEnum.MAIN_MENU);
        NextChatStateContextHolder.setValue(ChatState.MAIN_MENU);
    }


}
