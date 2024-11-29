package com.example.weathermonitor.service;

import com.example.weathermonitor.model.Alert;
import com.example.weathermonitor.model.ThresholdDirection;
import com.example.weathermonitor.model.User;
import com.example.weathermonitor.model.Weather;

import com.example.weathermonitor.repository.AlertRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final WeatherService weatherService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public AlertService(AlertRepository alertRepository, WeatherService weatherService, JavaMailSender javaMailSender) {
        this.alertRepository = alertRepository;
        this.weatherService = weatherService;
        this.javaMailSender = javaMailSender;
    }

    public Alert createAlert(String city, double threshold, String parameter, ThresholdDirection direction, String userEmail) {
        Alert alert = new Alert(city, threshold, parameter, direction, userEmail);
        alertRepository.addAlert(alert);
        return alert;
    }

    public List<Alert> getAlertsByUser(String email) {
        return alertRepository.getAlertsByUser(email);
    }

    @Scheduled(fixedRate = 60000) // Check weather every minute
    public void checkAlert() {
        List<Alert> alerts = alertRepository.getAlerts();
        for (Alert alert : alerts) {
            Weather weather = weatherService.getWeather(alert.getCity(), "metric", "en");

            double value = switch (alert.getParameter()) {
                case "humidity" -> weather.getHumidity();
                case "pressure" -> weather.getPressure();
                default -> weather.getTemperature();
            };

            boolean conditionMet = false;

            // Check the threshold condition
            if (alert.getDirection().equals(ThresholdDirection.BELOW) && value < alert.getThreshold()) {
                conditionMet = true;
            } else if (alert.getDirection().equals(ThresholdDirection.ABOVE) && value > alert.getThreshold()) {
                conditionMet = true;
            }

            if (conditionMet) {
                sendEmailAlert(alert);
            }
        }
    }

    private void sendEmailAlert(Alert alert) {
        try {
            String subject = "Weather Alert: " + alert.getCity();
            String body = "Hello " + alert.getUser() + ",\n\n" +
                    "The weather in " + alert.getCity() + " has triggered an alert!\n" +
                    "Parameter: " + alert.getParameter() + "\n" +
                    "Current Value: " + alert.getThreshold() + "\n" +
                    "Alert: " + (alert.getDirection().equals(ThresholdDirection.ABOVE) ? "Above" : "Below") + " threshold.\n\n";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("tudoreduard2004@gmail.com");
            helper.setTo(alert.getUser());
            helper.setSubject(subject);
            helper.setText(body);

            javaMailSender.send(message);

            System.out.println("Email sent to: " + alert.getUser());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
