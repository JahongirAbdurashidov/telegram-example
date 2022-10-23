package uz.jahongir.jahongirme.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.jahongir.jahongirme.context_holders.SendMessageContextHolder;
import uz.jahongir.jahongirme.properties.BotProperties;
import uz.jahongir.jahongirme.resolvers.KeyboardResolver;
import uz.jahongir.jahongirme.resolvers.NextChatStateResolver;
import uz.jahongir.jahongirme.resolvers.Resolver;
import uz.jahongir.jahongirme.utils.CommonUtil;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final Resolver resolver;
    private final KeyboardResolver keyboardResolver;
    private final NextChatStateResolver nextChatStateResolver;

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        CommonUtil.setUpAllContextHolders(update);

        try {

            resolver.resolve();
            nextChatStateResolver.resolve();
            keyboardResolver.resolve();

            SendMessage sendMessage = SendMessageContextHolder.getValue();

            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        CommonUtil.clearAllContextHolders();
    }


}
