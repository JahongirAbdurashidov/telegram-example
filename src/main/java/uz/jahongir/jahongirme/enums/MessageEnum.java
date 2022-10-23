package uz.jahongir.jahongirme.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jahongir.jahongirme.context_holders.LocaleContextHolder;

@Getter
@AllArgsConstructor
public enum MessageEnum {

    PLEASE_SEND_US_YOUR_PHONE("Iltimos bizga telefon raqamizni yuboring!", "Please share us your phone number!"),

    PLEASE_SEND_US_YOUR_PAYMENT_MOBILE_PHONE("Iltimos mobil telefon raqamizni yuboring!", "Please share us your mobile phone number!"),
    HOME_PHONE_MENU("Tanlang!", "Select!"),
    PAYMENT_EXAMPLE_WITH_MOBILE_PHONE("Telefon raqamini operator kodi bilan kiriting yoki kontakt ko'rinishida yuboring", "Enter the phone number with the operator code or send it in the contact form"),

    PLEASE_SEND_US_YOUR_PAYMENT_CITY_PHONE("Iltimos shahar mobil telefon raqamizni yuboring!", "Please share us your city mobile phone number!"),
    PAYMENT_EXAMPLE_WITH_CITY_PHONE(Emoji.NOTE_DIAMOND.getEmoji() + "  SHAHAR TELEFONI\n" +
            "\n" +
            "Telefon raqamini shahar kodi bilan kiriting:", Emoji.NOTE_DIAMOND.getEmoji() + " CITY TELEPHONE\n" +
            "\n" +
            "Enter the phone number with the area code:"),

    PAYMENT_EXAMPLE_WITH_REGION_PHONE(Emoji.NOTE_DIAMOND.getEmoji() + "  VILOYAT TELEFONI\n" +
            "\n" +
            "Telefon raqamini shahar kodi bilan kiriting:", Emoji.NOTE_DIAMOND.getEmoji() + " REGION TELEPHONE\n" +
            "\n" +
            "Enter the phone number with the area code:"),

    PAYMENT_EXAMPLE_WITH_DISTRICT_PHONE(Emoji.NOTE_DIAMOND.getEmoji() + "  VILOYAT TELEFONI\n" +
            "\n" +
            "Telefon raqamini shahar kodi bilan kiriting:", Emoji.NOTE_DIAMOND.getEmoji() + " REGION TELEPHONE\n" +
            "\n" +
            "Enter the phone number with the area code:"),



    PLEASE_ENTER_PAYMENT_AMOUNT("to'lov mablag'ini kiriting", " enter payment amount"),
    PLEASE_SEND_US_CORRECT_YOUR_PHONE("Siz boshqa telefon raqam yubordingiz! Iltimos bizga telefon raqamizni yuboring!", "You did not sent your phone number! Please share us your phone number!"),
    CONFIRM_PHONE("Sizning telefon raqamingiz qabul qilindi!", "Your phone number is confirmed"),
    PLEASE_SELECT_LOCALE("Iltimos til tanlang!", "Please select language!"),
    INCORRECT_MESSAGE("Siz noto`g`ri ma`lumot yubordingiz!", "Please send us correct answer!"),
    MAIN_MENU("Menu tanlang", "Please select menu!"),
    SETTINGS_MAIN_MENU("Menu tanlang", "Please select menu!"),
    PAY_HISTORY("To`lovlar tarixi", "Payment history"),
    PAYMENT_MAIN_MENU("Menu tanlang", "Please select menu!"),
    SEND_SUM("To`lov miqdori", "Sum!"),
    SUCCESSFULLY_PAYMENT("To`lov muvaffaqiyatli amalga oshirilidi", "Successfully payment!"),


    ;

    private final String uz;
    private final String en;

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
}
