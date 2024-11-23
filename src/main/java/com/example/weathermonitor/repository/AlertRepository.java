package com.example.weathermonitor.repository;

import com.example.weathermonitor.model.Alert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlertRepository {

    List<Alert> alerts;

    public AlertRepository() {
        alerts = new ArrayList<>();
    }

    public void addAlert(Alert alert) {
        alerts.add(alert);
    }

    public void removeAlert(Alert alert) {
        alerts.remove(alert);
    }

    public Alert getAlert(Long id) {
        for (Alert alert : alerts) {
            if (alert.getId().equals(id))
                return alert;
        }
        return null;
    }

    public List<Alert> getAlerts() {
        return this.alerts;
    }

    public List<Alert> getAlertsByUser(String email) {
        List<Alert> alerts = new ArrayList<>();
        for (Alert alert : getAlerts()) {
            if (alert.getUser().equals(email))
                alerts.add(alert);
        }
        return alerts;
    }
}
