package uz.jahongir.jahongirme.resolvers;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.jahongir.jahongirme.context_holders.ChatContextHolder;
import uz.jahongir.jahongirme.context_holders.SendMessageContextHolder;
import uz.jahongir.jahongirme.entities.ChatEntity;
import uz.jahongir.jahongirme.enums.ButtonMessageEnum;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.payloads.CustomSendMessage;
import uz.jahongir.jahongirme.services.ChatService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeyboardResolver {

    private final ChatService chatService;

    public void resolve() {
        Chat chat = ChatContextHolder.getValue();
        String chatId = Long.toString(chat.getId());

        ChatEntity chatEntity = chatService.findByChatIdElseThrow(chatId);

        ChatState chatState = chatEntity.getChatState();

        switch (chatState) {
            case LOCALE:
                resolveLocaleState();
                break;
            case SHARE_CONTACT:
                resolveShareContactState();
                break;
            case MAIN_MENU:
                resolveMainMenuState();
                break;

            case PAYMENT:
                resolverPaymentState();
                break;
            case ADD_PAYMENT_MOBILE_PHONE:
                resolverPaymentMobilePhone();
                break;

            case HOME_PHONE_MENU:
                resolverHomePhoneMenuState();
                break;
            case REGION_PHONE:
                resolveRegionState();
                break;
            case CITY_PHONE:
                resolveCityState();
                break;

            case SUM_ADD_PAYMENT_MOBILE_PHONE:
            case SUM_REGION_PHONE:
            case SUM_CITY_PHONE:
                cancelKeyboard();
                break;

            case SETTINGS:
                resolveSettingsState();
                break;
            case CHANGE_LOCALE:
                resolveChangeLocaleState();
                break;
            case CHANGE_PHONE:
                resolveChangePhoneState();
                break;
            default:
                backKeyboard();
        }

    }

    public void resolveLocaleState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);


        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton uzButton = new KeyboardButton();
        uzButton.setText(ButtonMessageEnum.UZ.value());
        keyboardFirstRow.add(uzButton);

        KeyboardButton enButton = new KeyboardButton();
        enButton.setText(ButtonMessageEnum.EN.value());
        keyboardFirstRow.add(enButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveShareContactState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(ButtonMessageEnum.PLEASE_SEND_US_YOUR_PHONE.value());
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveMainMenuState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton payments = new KeyboardButton();
        payments.setText(ButtonMessageEnum.PAYMENT.value());

        keyboardFirstRow.add(payments);

        KeyboardButton payHistory = new KeyboardButton();
        payHistory.setText(ButtonMessageEnum.PAY_HISTORY.value());
        keyboardFirstRow.add(payHistory);


        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();

        KeyboardButton settings = new KeyboardButton();
        settings.setText(ButtonMessageEnum.SETTINGS.value());
        keyboardSecondRow.add(settings);

        keyboard.add(keyboardSecondRow);


        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveSettingsState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton locale = new KeyboardButton();
        locale.setText(ButtonMessageEnum.LOCALE.value());
        keyboardFirstRow.add(locale);

        KeyboardButton changePhone = new KeyboardButton();
        changePhone.setText(ButtonMessageEnum.CHANGE_PHONE.value());
        keyboardFirstRow.add(changePhone);

        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardSecondRow.add(backButton);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolverPaymentState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton paymentMobilePhone = new KeyboardButton();
        paymentMobilePhone.setText(ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.value());
        keyboardFirstRow.add(paymentMobilePhone);


        KeyboardButton homePhoneButton = new KeyboardButton();
        homePhoneButton.setText(ButtonMessageEnum.PAYMENT_WITH_HOME_MENU.value());
        keyboardFirstRow.add(homePhoneButton);


        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardThreeRow = new KeyboardRow();

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardThreeRow.add(backButton);
        keyboard.add(keyboardThreeRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolverHomePhoneMenuState() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton region = new KeyboardButton();
        region.setText(ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_REGION_PHONE.value());
        keyboardFirstRow.add(region);

        KeyboardButton city = new KeyboardButton();
        city.setText(ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_CITY_PHONE.value());
        keyboardFirstRow.add(city);

        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardThreeRow = new KeyboardRow();

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardThreeRow.add(backButton);
        keyboard.add(keyboardThreeRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }


    public void resolveRegionState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstButtons = new KeyboardRow();
        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardFirstButtons.add(backButton);
        keyboard.add(keyboardFirstButtons);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveCityState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstButtons = new KeyboardRow();
        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardFirstButtons.add(backButton);
        keyboard.add(keyboardFirstButtons);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolverPaymentMobilePhone() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();


        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardFirstRow.add(backButton);

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void backKeyboard() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();


        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardFirstRow.add(backButton);

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void cancelKeyboard() {
        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();


        KeyboardButton cancelButton = new KeyboardButton();
        cancelButton.setText(ButtonMessageEnum.CANCEL.value());
        keyboardFirstRow.add(cancelButton);

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveChangeLocaleState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);


        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton uzButton = new KeyboardButton();
        uzButton.setText(ButtonMessageEnum.UZ.value());
        keyboardFirstRow.add(uzButton);

        KeyboardButton enButton = new KeyboardButton();
        enButton.setText(ButtonMessageEnum.EN.value());
        keyboardFirstRow.add(enButton);

        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardSecondRow.add(backButton);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void resolveChangePhoneState() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        CustomSendMessage sendMessage = SendMessageContextHolder.getValue();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);


        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton shareContactButton = new KeyboardButton();
        shareContactButton.setText(ButtonMessageEnum.PLEASE_SEND_US_YOUR_PHONE.value());
        shareContactButton.setRequestContact(true);
        keyboardFirstRow.add(shareContactButton);

        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(ButtonMessageEnum.BACK.value());
        keyboardSecondRow.add(backButton);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
