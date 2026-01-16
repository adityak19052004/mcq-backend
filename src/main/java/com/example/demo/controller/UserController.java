package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------------- LOGIN (ADMIN / USER) ----------------
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {

        Optional<User> foundUser =
                userRepository.findByUsernameAndPassword(
                        user.getUsername(),
                        user.getPassword()
                );

        // ❌ INVALID LOGIN
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // ✅ VALID LOGIN → RETURN FULL USER JSON
        return ResponseEntity.ok(foundUser.get());
    }

    // ---------------- ADD USER (ADMIN USE) ----------------
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
