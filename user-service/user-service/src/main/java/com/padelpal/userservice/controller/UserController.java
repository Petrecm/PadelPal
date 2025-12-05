package com.padelpal.userservice.controller;

import com.padelpal.userservice.model.User;
import com.padelpal.userservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Create user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return repository.save(user);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            // here we return ResponseEntity<String>
            return ResponseEntity.badRequest().body("User not found");
        }

        // here we return ResponseEntity<User>
        // method return type is ResponseEntity<?> so both are allowed
        return ResponseEntity.ok(optionalUser.get());
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
