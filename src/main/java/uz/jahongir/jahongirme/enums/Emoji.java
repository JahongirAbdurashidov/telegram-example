package uz.jahongir.jahongirme.enums;

import com.vdurmont.emoji.EmojiParser;

public enum Emoji {

    PHONE(":phone:"),
    PAYMENT(":money_with_wings:"),
    MOBILE_PHONE(":iphone:"),
    FLAG_UZ(":uz:"),
    FLAG_US(":us:"),
    SETTING(":gear:"),
    BACK(":arrow_right_hook:"),
    NOTE_DIAMOND(":small_blue_diamond:"),
    CANCEL(":no_entry_sign:"),
    LANG(":tongue:"),
    PAY_HISTORY(":book:")

    ;

    private final String code;

    Emoji(String code) {
        this.code = code;
    }

    public String getEmoji() {
        return EmojiParser.parseToUnicode(this.code);
    }
}
