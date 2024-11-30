package com.example.weathermonitor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "City name cannot be blank")
    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false)
    private double threshold;

    @Column(nullable = false)
    private ThresholdDirection direction;

    @NotBlank(message = "Parameter cannot be blank")
    @Column(nullable = false, length = 50)
    private String parameter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    public Alert() {}

    public Alert(String city, double threshold, String parameter, ThresholdDirection direction, User user) {
        this.city = city;
        this.threshold = threshold;
        this.parameter = parameter;
        this.direction = direction;
        this.user = user;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getParameter() {
        return parameter;
    }

    public ThresholdDirection getDirection() {
        return direction;
    }

    public double getThreshold() {
        return threshold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
