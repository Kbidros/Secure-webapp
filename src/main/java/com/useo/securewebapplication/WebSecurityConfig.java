package com.useo.securewebapplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
/*
Denna WebSecurityConfig-klass har jag skapat för att hantera säkerhetskonfigurationen i min Spring Boot-applikation.

Säkerhetsfilter:
Jag konfigurerar en SecurityFilterChain-bean för att specificera säkerhetsreglerna.
Åtkomstkontroll: Jag tillåter alla att komma åt startsidan och CSS-filer (permitAll()), medan sidor som registrering
och användarhantering kräver ADMIN-roll.
Inloggning och utloggning: Jag har anpassat inloggningssidan till /login och satt / som standard-sida efter lyckad inloggning.
Vid utloggning omdirigeras användare till /logout.

AccessDeniedHandler:
Skapar en AccessDeniedHandler-bean som omdirigerar till en anpassad 403-sida när åtkomst nekas.

Användarhantering:
Konfigurerar en InMemoryUserDetailsManager-bean för att hantera användarautentisering i minnet.
Skapar två användare: en med ADMIN-roll och en med USER-roll, och använder en PasswordEncoder för att kryptera deras lösenord.
PasswordEncoder:

Skapar och returnerar en BCryptPasswordEncoder-bean för att kryptera lösenord vid registrering och autentisering.
Denna klass ser till att min applikation har en robust säkerhetskonfiguration, hanterar åtkomstkontroller, och säkerställer
att användardata är säkert lagrade och hanterade.
*/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**").permitAll()
                        .requestMatchers("/register", "/users", "/registrationsuccess", "/delete").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/logout")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = passwordEncoder();
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

