package uz.jahongir.jahongirme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uz.jahongir.jahongirme.context_holders.ApplicationContextHolder;

@SpringBootApplication
public class JahongirMeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JahongirMeApplication.class, args);
        ApplicationContextHolder.setValue(run);
    }

}
