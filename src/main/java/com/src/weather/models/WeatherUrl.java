package com.src.weather.models;

public class WeatherUrl {

    private final String url;

    private final String apiKey;

    public WeatherUrl(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}
