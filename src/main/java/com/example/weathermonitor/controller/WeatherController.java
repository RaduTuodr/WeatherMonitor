package com.example.weathermonitor.controller;

import com.example.weathermonitor.model.Weather;
import com.example.weathermonitor.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}/{units}/{language}")
    public ResponseEntity<Weather> getWeather(@PathVariable("city") String city,
                                             @PathVariable("units") String units,
                                             @PathVariable("language") String language) {

        Weather weather = weatherService.getWeather(city, units, language);

        return ResponseEntity.ok(weather);
    }
}
