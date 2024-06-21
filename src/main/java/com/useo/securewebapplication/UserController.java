package com.useo.securewebapplication;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/*
Det här är min UserController-klass, som hanterar olika interaktioner i webbgränssnittet för min applikation.
Jag injicerar UserService för att hantera användarrelaterade tjänster.
Med @GetMapping("/users") hämtar jag och visar en lista av användare, som kan filtreras baserat på användarnamn.
Jag visar ett registreringsformulär med @GetMapping("/register") och hanterar registreringsförfrågningar med @PostMapping("/register").
Jag hanterar radering av användare baserat på ID med @PostMapping("/delete") och visar en bekräftelse på raderingen med @GetMapping("/delete").
Jag visar en utloggningssida med @GetMapping("/logout") och en anpassad 403-sida vid åtkomstförbud med @GetMapping("/403").
* */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(@RequestParam(name = "search", required = false) String search, Model model) {
        List<MyUsers.User> users = (search == null) ? userService.getAllUsers() : userService.findUsersByUsername(search);
        if (users.isEmpty()) {
            model.addAttribute("message", "User could not be found");
        }
        // Maskerar e-postadresser innan de visas på klienten
        users.forEach(user -> user.setEmail(MaskingUtils.maskEmail(user.getEmail())));
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationDto registrationDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registrationDto", registrationDto);
            return "register";
        }
        userService.saveUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());
        return "redirect:/registrationsuccess";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/delete";
    }

    @GetMapping("/delete")
    public String showDeleteSuccess() {
        return "delete";
    }

    @GetMapping("/logout")
    public String showLogoutPage() {
        return "logout";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
