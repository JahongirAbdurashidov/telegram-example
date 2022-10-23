package uz.jahongir.jahongirme.payloads;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.jahongir.jahongirme.enums.ButtonMessageEnum;
import uz.jahongir.jahongirme.enums.MessageEnum;

public class CustomSendMessage extends SendMessage {

    public CustomSendMessage(@NonNull String chatId) {
        super();
        super.setChatId(chatId);
    }

    public void setChatId(String chatId) {
        throw new IllegalArgumentException("Already assign chat id");
    }

    public void setText(MessageEnum message){
        super.setText(message.value());
    }

    public void setText(ButtonMessageEnum message){
        super.setText(message.value());
    }
}
