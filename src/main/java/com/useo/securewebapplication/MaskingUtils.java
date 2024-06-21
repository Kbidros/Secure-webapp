package com.useo.securewebapplication;
/*
Denna MaskingUtils-klass har jag skapat för att maskera e-postadresser på ett säkert sätt.

Maskering av e-post: Metoden maskEmail tar en e-postadress och ersätter tecknen i användarnamnet
(förutom det första och sista) med stjärnor (*). Detta hjälper till att skydda användarens integritet genom att
dölja delar av deras e-postadress.

Logik för maskering:
Jag delar upp e-postadressen i två delar: användarnamn och domän.
Om användarnamnet är kortare än två tecken returneras e-postadressen utan ändringar.
Annars maskeras tecknen mellan det första och sista tecknet i användarnamnet med stjärnor.

Återstår den maskerade e-posten: Slutligen returnerar metoden den maskerade e-postadressen, som ser ut som
f****e@example.com för en e-postadress som fake@example.com.
Detta tillvägagångssätt säkerställer att e-postadresser visas på ett sätt som bevarar användarens integritet
utan att avslöja hela adressen.
*/
public class MaskingUtils {


    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (username.length() < 2) {
            return email;
        }

        StringBuilder maskedEmail = new StringBuilder();
        maskedEmail.append(username.charAt(0));

        for (int i = 1; i < username.length() - 1; i++) {
            maskedEmail.append('*');
        }

        maskedEmail.append(username.charAt(username.length() - 1));
        maskedEmail.append(domain);

        return maskedEmail.toString();
    }
}
