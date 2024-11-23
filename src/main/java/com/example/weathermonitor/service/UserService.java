package com.example.weathermonitor.service;

import com.example.weathermonitor.model.User;
import com.example.weathermonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String email) {
        User user = new User(email);
        userRepository.addUser(user);
        return user;
    }

    public User getUser(String email) {
        return userRepository.getUser(email);
    }

    public void updateUser(String email, User user) {
        userRepository.updateUser(email, user);
    }

    public User addFavouriteCityToUser(String email, String city) {
        User user = this.getUser(email);
        user.addFavoriteCity(city);
        userRepository.updateUser(user.getEmail(), user);
        return user;
    }
}
