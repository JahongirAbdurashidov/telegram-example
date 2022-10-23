package uz.jahongir.jahongirme.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jahongir.jahongirme.context_holders.LocaleContextHolder;

@Getter
@AllArgsConstructor
public enum ButtonMessageEnum {

    PLEASE_SEND_US_YOUR_PHONE(Emoji.PHONE.getEmoji() + " Telefon raqamni yuborish!!", Emoji.PHONE.getEmoji() + " Send contact"),
    PAYMENT(Emoji.PAYMENT.getEmoji() + " To'lovlar", Emoji.PHONE.getEmoji() + " Payments"),
    PAY_HISTORY(Emoji.PAY_HISTORY.getEmoji() + " To'lovlar tarixi", Emoji.PAY_HISTORY.getEmoji() + " Payments history"),
    PAYMENT_WITH_MOBILE_PHONE(Emoji.MOBILE_PHONE.getEmoji() + " Mobil operatorlar", Emoji.MOBILE_PHONE.getEmoji() + " Mobil operators"),
    PAYMENT_WITH_HOME_MENU(Emoji.PHONE.getEmoji() + " Uy telefoniyasi", Emoji.PHONE.getEmoji() + " Home phones"),

    PAYMENT_WITH_HOME_MENU_CITY_PHONE("Shahar telefoni", "City phones"),
    PAYMENT_WITH_HOME_MENU_REGION_PHONE("Viloyat telefoni", "Region phones"),
    PAYMENT_WITH_HOME_MENU_DISTRICT_PHONE("Tuman telefoni", "District phones"),

    UZ(Emoji.FLAG_UZ.getEmoji() + " " + Locale.UZ.getUz(), Emoji.FLAG_UZ.getEmoji() + " " + Locale.UZ.getEn()),
    EN(Emoji.FLAG_US.getEmoji() + " " + Locale.EN.getUz(), Emoji.FLAG_US.getEmoji() + " " + Locale.EN.getEn()),
    SETTINGS(Emoji.SETTING.getEmoji() + " Sozlamalar", Emoji.SETTING.getEmoji() + " Settings"),
    BACK(Emoji.BACK.getEmoji() + " Orqaga", Emoji.BACK.getEmoji() + " Back"),
    CANCEL(Emoji.CANCEL.getEmoji() + " Bekor qilish", Emoji.CANCEL.getEmoji() + " Cancel"),

    LOCALE(Emoji.LANG.getEmoji() + " Dastur tilini boshaqarish", Emoji.LANG.getEmoji() + " Language"),
    CHANGE_PHONE(Emoji.PHONE.getEmoji() + " Telefon raqamni o`zgartirish", Emoji.PHONE.getEmoji() + " Change phone number"),
    NOT_FOUND("Topilmadi", "Not found"),
    ;

    private String uz;
    private String en;

    public String value() {
        return this.toString();
    }


    @Override
    public String toString() {
        switch (LocaleContextHolder.getValue()) {

            case EN:
                return this.en;
            case UZ:
            default:
                return this.uz;
        }

    }

    public static ButtonMessageEnum getByMessage(String message) {
        for (ButtonMessageEnum value : values()) {
            if (
                    value.uz.toLowerCase(java.util.Locale.ROOT).equals(message.toLowerCase(java.util.Locale.ROOT))
                            || value.en.toLowerCase(java.util.Locale.ROOT).equals(message.toLowerCase(java.util.Locale.ROOT))
            )
                return value;
        }
        NOT_FOUND.en = message;
        NOT_FOUND.uz = message;
        return NOT_FOUND;
    }
}
