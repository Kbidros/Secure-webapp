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

@Configuration // Markerar denna klass som en konfigurationsklass för Spring
@EnableWebSecurity // Aktiverar Spring Securitys webbsäkerhetsstöd
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
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
                        // Hanterar åtkomst nekad situationer genom att omdirigera till en anpassad sida ( i mitt fall /403 )
                        .accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        // Skapar en anpassad AccessDeniedHandler som omdirigerar till /403 vid åtkomst nekad
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = passwordEncoder();
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("123")) // Skapar en användare med rollen ADMIN och kodar lösenordet
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password")) // Samma fast för USER
                .roles("USER")
                .build();

        // Returnerar en InMemoryUserDetailsManager med de skapade användarna
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Skapar och returnerar en BCryptPasswordEncoder för lösenordskodning
}

