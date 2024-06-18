package com.useo.securewebapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
public class Tests {

    @Autowired
    private MockMvc mockMvc;

      /// Test för registrering av användare

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnRegistrationPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRegisterUserSuccessfully() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "newuser")
                        .param("password", "strongpassword123")
                        .param("email", "test@hotmail.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registrationsuccess"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailRegistrationWithInvalidData() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "")  // Invalid data
                        .param("password", "123")  // Too short
                        .param("email", "notanemail")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }


    /// Test för första-sidan

    @Test
    public void shouldAllowAccessToHomePageForUnauthenticatedUser() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home")); // Antag att "index" är namnet på din startsidans vy
    }

    @Test
    public void shouldAllowAccessToHomePageForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/").with(user("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home")); // Använd samma vy som ovan
    }


    /// Test för delete

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldSuccessfullyDeleteUser() throws Exception {
        mockMvc.perform(post("/delete")
                        .param("userId", "1") // Antag att det finns en användare med ID 1
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users")); // Antag att efter borttagning omdirigeras användaren tillbaka till användarlistan
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldFailToDeleteUserDueToInsufficientPermissions() throws Exception {
        mockMvc.perform(post("/delete")
                        .param("userId", "1") // Försök ta bort samma användare som ovan utan admin-rättigheter
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden()); // Förvänta dig ett 403 Forbidden svar
    }




}

