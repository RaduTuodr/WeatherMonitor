package com.example.weathermonitor.controller;

import com.example.weathermonitor.model.Weather;
import com.example.weathermonitor.service.WeatherService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/{city}/{units}/{language}")
    public ResponseEntity<String> getWeather(@PathVariable("city") String city,
                                             @PathVariable("units") String units,
                                             @PathVariable("language") String language) {

        Weather weather = weatherService.getWeather(city, units, language);

        String weatherJsonString = null;
        try {
            weatherJsonString = objectMapper.writeValueAsString(weather);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return ResponseEntity.ok(weatherJsonString);
    }
}
