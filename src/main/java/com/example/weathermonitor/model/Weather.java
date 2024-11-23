package com.example.weathermonitor.model;

import javax.persistence.*;

@Entity
@Table(name = "weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private double temperature;
    private double humidity;
    private double pressure;
    private String description;
    private String timezone;

    public Weather(String city, double temperature, double humidity, double pressure, String description, String timezone) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
        this.timezone = timezone;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public String getDescription() {
        return description;
    }

    public String getTimezone() {
        return timezone;
    }
}
