package com.hamels.daybydayegg.Utils;

import android.util.Patterns;

public class FormatUtils {
    public static boolean isPasswordFormat(String password) {
        String passwordFormat = "[a-zA-Z0-9]{6,12}";

        return password.matches(passwordFormat);
    }

    public static boolean isCellphoneFormat(String phone) {
        String phoneFormat = "[0-9]{10}";

        return phone.matches(phoneFormat);
    }

    public static boolean isEmailFormat(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
