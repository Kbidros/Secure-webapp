package com.useo.securewebapplication;

public class HtmlUtils {

    // Sanerar / "tvättar" en e-postadress genom att konvertera specialtecken till deras motsvarande HTML-entiteter.
    public static String sanitizeEmail(String email) {
        return org.springframework.web.util.HtmlUtils.htmlEscape(email);
    }

    // Sanerar en sträng genom att konvertera specialtecken till deras motsvarande HTML-entiteter.
    public static String sanitize(String input) {
        return org.springframework.web.util.HtmlUtils.htmlEscape(input);
    }
}
