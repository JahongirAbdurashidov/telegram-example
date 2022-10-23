package uz.jahongir.jahongirme.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.jahongir.jahongirme.enums.ButtonMessageEnum;
import uz.jahongir.jahongirme.enums.Emoji;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyBoardService {


    public void setGiveContactKeyboard(SendMessage sendMessage) {

        final String btnText = Emoji.PHONE.getEmoji()
                .concat(ButtonMessageEnum.PLEASE_SEND_US_YOUR_PHONE.value());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(btnText);
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void setLocalesKeyboard(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
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

    public void setMainMenuKeyboard(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton settings = new KeyboardButton();
        settings.setText(ButtonMessageEnum.SETTINGS.value());
        keyboardFirstRow.add(settings);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void setSettingsKeyboard(SendMessage sendMessage) {

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

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
