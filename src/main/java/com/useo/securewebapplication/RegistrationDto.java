package com.useo.securewebapplication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
/*
Den här RegistrationDto-klassen använder jag för att validera användarinmatning vid registrering.

Användarnamn: Jag använder annoteringen @NotEmpty för att se till att användarnamnet inte är tomt.

E-post: För e-postadressen använder jag flera annoteringar:
@NotEmpty för att säkerställa att fältet inte är tomt.
@Email för att säkerställa att e-postadressen är i ett giltigt format.
@Pattern för att begränsa till specifika e-postdomäner (gmail, hotmail, eller outlook).

Lösenord: För lösenordet använder jag:
@NotEmpty för att säkerställa att fältet inte är tomt.
@Size för att säkerställa att lösenordet är minst 8 tecken långt.

Getters och setters finns för att hämta och sätta värdena för dessa fält. Detta säkerställer att användarinmatning är korrekt formaterad och följer de specificerade reglerna vid registrering.
*/

// Denna klass är till för att validering av email och lösenord görs.

public class RegistrationDto {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@(gmail|hotmail|outlook)\\.com$", message = "Email should be a either a Gmail, Hotmail, or Outlook address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
