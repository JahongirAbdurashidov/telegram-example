package uz.jahongir.jahongirme.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bot")
@Data
public class BotProperties {
    private String username;
    private String token;

}
