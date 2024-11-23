package com.example.weathermonitor.controller;

import com.example.weathermonitor.model.Alert;
import com.example.weathermonitor.model.ThresholdDirection;
import com.example.weathermonitor.model.User;
import com.example.weathermonitor.service.AlertService;
import com.example.weathermonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;
    private final UserService userService;

    @Autowired
    public AlertController(AlertService alertService, UserService userService) {
        this.alertService = alertService;
        this.userService = userService;
    }

    @PostMapping("/{city}/{threshold}/{parameter}/{direction}/{message}/{email}")
    public ResponseEntity<Alert> createAlert(@PathVariable String city,
                                             @PathVariable double threshold,
                                             @PathVariable String parameter,
                                             @PathVariable ThresholdDirection direction,
                                             @PathVariable String message,
                                             @PathVariable String email) {

        User user = userService.getUser(email);
        Alert alert = alertService.createAlert(city, threshold, parameter, direction, message, email);

        user.addAlert(alert);
        userService.updateUser(user.getEmail(), user);

        return ResponseEntity.ok(alert);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Alert>> getAlert(@PathVariable String email) {
        List<Alert> alertsOfUser = alertService.getAlertsByUser(email);
        return ResponseEntity.ok(alertsOfUser);
    }
}
