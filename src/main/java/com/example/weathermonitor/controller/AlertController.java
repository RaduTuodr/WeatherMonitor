package com.example.weathermonitor.controller;

import com.example.weathermonitor.model.Alert;
import com.example.weathermonitor.model.ThresholdDirection;
import com.example.weathermonitor.model.User;
import com.example.weathermonitor.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Autowired
    public AlertController(AlertService alertService, UserService userService, JwtUtil jwtUtil) {
        this.alertService = alertService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{city}/{threshold}/{parameter}/{direction}/{email}")
    public ResponseEntity<Alert> createAlert(@PathVariable String city,
                                             @PathVariable double threshold,
                                             @PathVariable String parameter,
                                             @PathVariable String direction,
                                             @PathVariable String email,
                                             @RequestBody String token) {
        try {
            User user = userService.getUser(email);
            if (!jwtUtil.validateToken(token, email))
                return ResponseEntity.status(401).body(null);

            ThresholdDirection direction1 = (direction.equals("above")) ? ThresholdDirection.ABOVE : ThresholdDirection.BELOW;

            Alert alert = alertService.createAlert(city, threshold, parameter, direction1, email);

            user.addAlert(alert);
            userService.updateUser(user.getEmail(), user);

            return ResponseEntity.ok(alert);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Alert>> getAlert(@PathVariable String email) {
        List<Alert> alertsOfUser = alertService.getAlertsByUser(email);
        return ResponseEntity.ok(alertsOfUser);
    }
}
