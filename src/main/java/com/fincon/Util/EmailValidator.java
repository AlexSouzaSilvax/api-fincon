package com.fincon.Util;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String pEmail) {
        if (pEmail == null || pEmail.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(pEmail).matches();
    }
}