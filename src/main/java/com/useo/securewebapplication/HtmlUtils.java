package com.useo.securewebapplication;

public class HtmlUtils {

    public static String sanitizeEmail(String email) {
        return org.springframework.web.util.HtmlUtils.htmlEscape(email);
    }

    public static String sanitize(String input) {
        return org.springframework.web.util.HtmlUtils.htmlEscape(input);
    }
}
