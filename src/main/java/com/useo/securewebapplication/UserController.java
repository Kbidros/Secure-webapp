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

// Kontroller-klass som hanterar webbgränssnittets interaktioner
@Controller
public class UserController {

    // Injektion av UserService för att hantera användarrelaterade tjänster
    @Autowired
    private UserService userService;

    // Hanterar GET-förfrågningar för att visa en lista av användare, kan filtreras med 'search'
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

    // Hanterar GET-förfrågningar för att visa registreringsformuläret
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }

    // Hanterar POST-förfrågningar för att registrera en ny användare
    @PostMapping("/register")
    public String register(@Valid RegistrationDto registrationDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registrationDto", registrationDto);
            return "register";
        }
        userService.saveUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());
        return "redirect:/registrationsuccess";
    }

    // Hanterar POST-förfrågningar för att radera en användare baserat på ID
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/delete";
    }

    // Visar ett meddelande om att användaren har raderats framgångsrikt
    @GetMapping("/delete")
    public String showDeleteSuccess() {
        return "delete";
    }

    // Visar sidan för framgångsrik utloggning
    @GetMapping("/logout")
    public String showLogoutPage() {
        return "logout";
    }

    // Visar en anpassad 403-sida vid åtkomstförbud
    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
