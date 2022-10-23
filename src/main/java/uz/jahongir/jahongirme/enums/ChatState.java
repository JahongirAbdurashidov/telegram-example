package uz.jahongir.jahongirme.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatState {

    REGISTRATION,
    LOCALE,
    MAIN_MENU,
    SHARE_CONTACT,

    PAYMENT,

    ADD_PAYMENT_MOBILE_PHONE,
    SUM_ADD_PAYMENT_MOBILE_PHONE,

    HOME_PHONE_MENU,

    CITY_PHONE,
    SUM_CITY_PHONE,

    REGION_PHONE,
    SUM_REGION_PHONE,


    DISTRICT_PHONE,
    SUM_DISTRICT_PHONE,


    PAY_HISTORY,


    SETTINGS,
    CHANGE_PHONE,
    CHANGE_LOCALE,

    ;


}
