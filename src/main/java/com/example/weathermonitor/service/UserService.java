package com.example.weathermonitor.service;

import com.example.weathermonitor.model.User;
import com.example.weathermonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String email, String password) {
        if (userRepository.existsById(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(email, hashedPassword);
        return userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public void updateUser(String email, User user) {
        if (!userRepository.existsById(email)) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        userRepository.save(user);
    }
}
