package com.example.weathermonitor.service;

import com.example.weathermonitor.Utils;
import com.example.weathermonitor.model.Weather;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public WeatherService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
                .build();
    }

    public Weather getWeather(String city, String units, String lang) {

        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", units)
                        .queryParam("lang", lang)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);

        JSONObject jsonObject = new JSONObject(result.block());

        if (jsonObject.has("cod") && Integer.valueOf(404).equals(jsonObject.getInt("cod"))) {
            System.out.println("Error: " + jsonObject.getString("message"));
            return null;
        }

        JSONObject description = (JSONObject) jsonObject.getJSONArray("weather").get(0);

        return new Weather(jsonObject.getString("name"),
                jsonObject.getJSONObject("main").getDouble("temp"),
                jsonObject.getJSONObject("main").getDouble("humidity"),
                jsonObject.getJSONObject("main").getDouble("pressure"),
                description.getString("description"),
                Utils.getTimezoneName(jsonObject.getInt("timezone"))
        );
    }
}
