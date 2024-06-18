package com.useo.securewebapplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUsers.User, Long> {
    List<MyUsers.User> findByUsernameContainingIgnoreCase(String username);

    Optional<MyUsers.User> findByUsername(String username);


}