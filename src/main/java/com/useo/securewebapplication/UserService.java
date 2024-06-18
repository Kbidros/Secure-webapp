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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

   @Autowired
   private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // Skapar en ny användare med validerade och hashade lösenord och sparar den både i databasen och i minnesbaserad autentisering
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
                    .password(hashedPassword)  // Använder det hashade lösenordet
                    .roles("USER")  // Anta default roll som "USER"
                    .build();
            inMemoryUserDetailsManager.createUser(user);

            log.info("New user registered successfully: {}", username);
        } catch (Exception e) {
            log.error("Registration failed for user: {}", username, e);
            throw e;
        }
    }

    // Tar bort en användare från databasen om den finns
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

    // Returnerar en lista av alla registrerade användare
    public List<MyUsers.User> getAllUsers() {
        return userRepository.findAll();
    }

    // Hittar och returnerar användare vars användarnamn innehåller den angivna strängen
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