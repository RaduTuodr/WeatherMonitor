package com.example.weathermonitor.model;

import jakarta.validation.constraints.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Email(message = "Invalid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private List<Alert> alerts = new ArrayList<>();

    @Column(name = "city")
    private List<String> favoriteCities = new ArrayList<>();

    public User() {}
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public List<String> getFavoriteCities() {
        return favoriteCities;
    }

    public void setFavoriteCities(List<String> favoriteCities) {
        this.favoriteCities = favoriteCities;
    }

    public void addFavoriteCity(String city) {
        if (!favoriteCities.contains(city)) {
            favoriteCities.add(city);
        }
    }

    public void removeFavoriteCity(String city) {
        favoriteCities.remove(city);
    }

    public void addAlert(Alert alert) {
        alert.setUser(this);
        alerts.add(alert);
    }

    public void removeAlert(Alert alert) {
        alerts.remove(alert);
        alert.setUser(null);
    }
}
