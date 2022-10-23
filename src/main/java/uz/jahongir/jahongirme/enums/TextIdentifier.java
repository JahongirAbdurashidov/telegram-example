package uz.jahongir.jahongirme.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TextIdentifier {
    START(
            "/start",
            "/start"
    ),

    BTN_LOCALE_UZ(
            ButtonMessageEnum.UZ.getUz(),
            ButtonMessageEnum.UZ.getEn()
    ),

    BTN_LOCALE_EN(
            ButtonMessageEnum.EN.getUz(),
            ButtonMessageEnum.EN.getEn()
    ),

    BTN_SETTINGS(
            ButtonMessageEnum.SETTINGS.getUz(),
            ButtonMessageEnum.SETTINGS.getEn()
    ),

    BTN_CHANGE_LOCALE(
            ButtonMessageEnum.LOCALE.getUz(),
            ButtonMessageEnum.LOCALE.getEn()
    ),


    BTN_CHANGE_PHONE(
            ButtonMessageEnum.CHANGE_PHONE.getUz(),
            ButtonMessageEnum.CHANGE_PHONE.getEn()
    ),

    BTN_SEND_CONTACT(
            ButtonMessageEnum.PLEASE_SEND_US_YOUR_PHONE.getUz(),
            ButtonMessageEnum.PLEASE_SEND_US_YOUR_PHONE.getEn()
    ),

    BTN_PAYMENT(
            ButtonMessageEnum.PAYMENT.getUz(),
            ButtonMessageEnum.PAYMENT.getEn()
    ),

    BTN_PAY_HISTORY(
            ButtonMessageEnum.PAY_HISTORY.getUz(),
            ButtonMessageEnum.PAY_HISTORY.getEn()

    ),
    BTN_PAYMENT_MOBILE_PHONE(
            ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.getUz(),
            ButtonMessageEnum.PAYMENT_WITH_MOBILE_PHONE.getEn()
    ),


    BTN_PAYMENT_HOME_MENU(
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU.getUz(),
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU.getEn()
    ),

    BTN_PAYMENT_HOME_MENU_CITY_PHONE(
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_CITY_PHONE.getUz(),
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_CITY_PHONE.getEn()
    ),

    BTN_PAYMENT_HOME_MENU_REGION_PHONE(
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_REGION_PHONE.getUz(),
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_REGION_PHONE.getEn()
    ),


    BTN_PAYMENT_HOME_MENU_DISTRICT_PHONE(
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_DISTRICT_PHONE.getUz(),
            ButtonMessageEnum.PAYMENT_WITH_HOME_MENU_DISTRICT_PHONE.getEn()
    ),




    BTN_BACK(
            ButtonMessageEnum.BACK.getUz(),
            ButtonMessageEnum.BACK.getEn()
    ),
    BTN_CANCEL(
            ButtonMessageEnum.CANCEL.getUz(),
            ButtonMessageEnum.CANCEL.getEn()
    ),

    NOT_FOUND(
            "",
            ""
    );

    private String uz;
    private String en;

    public static TextIdentifier getByMessage(String message) {
        for (TextIdentifier value : values()) {
            if (
                    value.uz.toLowerCase(Locale.ROOT).equals(message.toLowerCase(Locale.ROOT))
                            || value.en.toLowerCase(Locale.ROOT).equals(message.toLowerCase(Locale.ROOT))
            )
                return value;
        }
        return NOT_FOUND;
    }
}
