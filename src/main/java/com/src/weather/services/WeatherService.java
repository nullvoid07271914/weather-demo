package com.src.weather.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.weather.models.Weather;
import com.src.weather.repositories.WeatherRepositories;

@Service
public class WeatherService {

	@Autowired
	private WeatherRepositories repo;

	public Optional<Weather> save(Weather weather) {
		Weather savedWeather = repo.save(weather);
		return Optional.ofNullable(savedWeather);
	}
}
