package com.example.weathermonitor.repository;

import com.example.weathermonitor.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        User checkUser = getUser(user.getEmail());
        if (checkUser != null) {
            throw new IllegalArgumentException("User already exists");
        }
        users.add(user);
    }

    public void updateUser(String email, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equals(email)) {
                users.set(i, updatedUser);
                break;
            }
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public List<User> getUsers() {
        return this.users;
    }
}
