package com.useo.securewebapplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Definierar ett repository-interface för User-entiteter, ärver metoder för CRUD-operationer från JpaRepository
public interface UserRepository extends JpaRepository<MyUsers.User, Long> {

    // Hämtar en lista av användare vars användarnamn innehåller den angivna strängen (case insensitive)
    List<MyUsers.User> findByUsernameContainingIgnoreCase(String username);

    // Hämtar en användare baserat på exakt användarnamn, returnerar ett Optional-objekt
    Optional<MyUsers.User> findByUsername(String username);
}
