package com.example.weathermonitor.controller;

import com.example.weathermonitor.dto.LoginRequest;
import com.example.weathermonitor.model.User;
import com.example.weathermonitor.security.JwtUtil;
import com.example.weathermonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/signup/{email}")
    public ResponseEntity<String> signup(@PathVariable String email, @RequestBody LoginRequest loginRequest) {
        try {
            String password = loginRequest.getPassword();
            User user = userService.createUser(email, password);

            String token = jwtUtil.generateToken(email);

            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(402).body("User with this email already exists");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Couldn't sign up user " + email);
        }
    }

    @PostMapping("/login/{email}")
    public ResponseEntity<String> login(@PathVariable String email, @RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.getUser(email);
            if (user == null)
                return ResponseEntity.status(401).body("User not found!");
            String password = loginRequest.getPassword();
            if (!passwordEncoder.matches(password, user.getPassword()))
                return ResponseEntity.status(401).body("Passwords don't match!");

            String token = jwtUtil.generateToken(email);

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
