package uz.jahongir.jahongirme.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Locale {
    UZ("O`zbek", "Uzbek"),
    EN("Ingliz tili", "English"),
    ;
    private final String uz;
    private final String en;

}
