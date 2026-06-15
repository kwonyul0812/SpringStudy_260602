package com.example.day5.util;


import java.util.Collections;

public class MaskingUtil {

    public static String maskCardNo(String cardNo) {
        if (cardNo == null || cardNo.length() < 8) return "****";
        return cardNo.substring(0, 4)
                + String.join("", Collections.nCopies(cardNo.length() - 8, "*"))
                + cardNo.substring(cardNo.length() - 4);
    }

    public static String maskAccount(String acc) {
        if (acc == null || acc.length() < 6) return acc;
        return acc.substring(0, 3) + "***" + acc.substring(acc.length() - 3);
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 8) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
