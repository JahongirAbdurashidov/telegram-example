package uz.jahongir.jahongirme.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.enums.Locale;
import uz.jahongir.jahongirme.utils.CommonUtil;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity extends AbstractEntity {

    @Column(name = "telegram_chat_id", unique = true)
    private String telegramChatId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "locale")
    private Locale locale;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_state")
    private ChatState chatState;

    @Column(name = "data", columnDefinition = "text")
    private String data;
}
