package com.useo.securewebapplication;

public class MaskingUtils {

    // Maskerar en e-postadress så att endast första och sista tecknet i användarnamnet är synliga,
    // medan resten ersätts av stjärnor (*).

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);  // Extraherar användarnamnet från e-posten.
        String domain = email.substring(atIndex);  // Extraherar domändelen från e-posten.

        if (username.length() < 2) {
            return email;
        }

        StringBuilder maskedEmail = new StringBuilder();
        maskedEmail.append(username.charAt(0));  // Lägger till det första tecknet av användarnamnet.

        // Lägger till maskeringstecken (*) för alla tecken mellan det första och sista tecknet i användarnamnet.
        for (int i = 1; i < username.length() - 1; i++) {
            maskedEmail.append('*');
        }

        maskedEmail.append(username.charAt(username.length() - 1));  // Lägger till det sista tecknet i användarnamnet.
        maskedEmail.append(domain);  // Lägger till domändelen.

        return maskedEmail.toString();  // Returnerar den maskerade e-postadressen.
    }
}
