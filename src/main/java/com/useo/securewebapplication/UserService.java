package com.useo.securewebapplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/*
Det här är min UserService-klass, som hanterar olika tjänster relaterade till användarhantering i min applikation.

Injektioner: Jag injicerar UserRepository, InMemoryUserDetailsManager, och PasswordEncoder för att hantera användarrelaterade operationer.
Registrering av användare: I saveUser-metoden skapar jag en ny användare genom att sanera och hash lösenordet, och
sparar användaren både i databasen och i minnesbaserad autentisering. Jag loggar relevant information under processen.
Radering av användare: I deleteUser-metoden försöker jag radera en användare baserat på användar-ID. Om användaren inte finns loggar jag en varning.
Hämta alla användare: I getAllUsers-metoden returnerar jag en lista över alla registrerade användare.
Sök användare efter användarnamn: I findUsersByUsername-metoden söker jag efter och returnerar användare vars
användarnamn innehåller den angivna strängen, och loggar resultatet.
Denna klass säkerställer att användare hanteras på ett säkert och strukturerat sätt, inklusive korrekt sanering och hashning av känslig data.
*/

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

   @Autowired
   private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void saveUser(String username, String password, String email) {
        try {
            String cleanEmail = HtmlUtils.sanitizeEmail(email);
            String cleanPassword = HtmlUtils.sanitize(password);
            String hashedPassword = passwordEncoder.encode(cleanPassword);

            log.debug("Creating a new account for user: {}", username);
            MyUsers.User newUser = new MyUsers.User();
            newUser.setUsername(username);
            newUser.setPassword(hashedPassword);
            newUser.setEmail(cleanEmail);
            userRepository.save(newUser);


            log.debug("Created user details for: {}", username);
            UserDetails user = User.builder()
                    .username(username)
                    .password(hashedPassword)
                    .roles("USER")
                    .build();
            inMemoryUserDetailsManager.createUser(user);

            log.info("New user registered successfully: {}", username);
        } catch (Exception e) {
            log.error("Registration failed for user: {}", username, e);
            throw e;
        }
    }

    public void deleteUser(Long userId) {
        log.debug("Attempting to delete user with ID: {}", userId);

        Optional<MyUsers.User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            log.info("User deleted: {}", user.get().getUsername());
        } else {
            log.warn("User not found with ID: {}", userId);
        }
    }

    public List<MyUsers.User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<MyUsers.User> findUsersByUsername(String username) {
        List<MyUsers.User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        if (users.isEmpty()) {
            log.warn("No users found with username containing: {}", username);
        } else {
            log.info("Found {} users with username containing: {}", users.size(), username);
        }
        return users;
    }

}