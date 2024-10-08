package com.useo.securewebapplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/delete").setViewName("delete");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/registrationsuccess").setViewName("registrationsuccess");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/403").setViewName("403");

    }
}
