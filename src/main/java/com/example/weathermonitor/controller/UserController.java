package com.example.weathermonitor.controller;

import com.example.weathermonitor.dto.LoginRequest;
import com.example.weathermonitor.model.User;
import com.example.weathermonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.getUser(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{email}")
    public ResponseEntity<User> createUser(@PathVariable String email, @RequestBody LoginRequest password) {
        User user = userService.createUser(email, password.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{email}/{favCity}")
    public ResponseEntity<User> addFavCityToUser(@PathVariable String email, @PathVariable String favCity) {
        User user = userService.addFavouriteCityToUser(email, favCity);
        return ResponseEntity.ok(user);
    }
}
