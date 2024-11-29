package com.example.weathermonitor.model;

import jakarta.validation.constraints.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", nullable = false)
    private String userEmail;

    public Alert() {
    }

    public Alert(String city, double threshold, String parameter, ThresholdDirection direction, String userEmail) {
        this.city = city;
        this.threshold = threshold;
        this.parameter = parameter;
        this.direction = direction;
        this.userEmail = userEmail;
    }

    public Long getId() {
        return this.id;
    }

    public void setUser(User user) {
        this.userEmail = userEmail;
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

    public String getUser() {
        return userEmail;
    }
}
