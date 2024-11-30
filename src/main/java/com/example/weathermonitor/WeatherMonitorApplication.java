package com.example.weathermonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.example.weathermonitor")
@EnableScheduling
@EntityScan("com.example.weathermonitor.*")
@ComponentScan(basePackages = { "com.example.weathermonitor.*" })
public class WeatherMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherMonitorApplication.class, args);
    }
}
