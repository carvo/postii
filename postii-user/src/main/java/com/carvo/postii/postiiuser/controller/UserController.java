package com.carvo.postii.postiiuser.controller;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        final Optional<User> user = userService.findUser(id);
        return user.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(user.get());
    }

    @GetMapping("/users/{id}/exists")
    public ResponseEntity<Void> userExists(@PathVariable Long id) {
        userService.validateUserExists(id);
        return ResponseEntity.noContent().build();
    }
}
