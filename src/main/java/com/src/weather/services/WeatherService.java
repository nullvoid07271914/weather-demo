package com.src.weather.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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

	@SuppressWarnings("unchecked")
	public Weather createWeatherObject(Map<String, Object> weatherRawData) {
		List<Map<String, Object>> weatherMap = (List<Map<String, Object>>) weatherRawData.get("weather");
		Map<String, Object> mainMap = (Map<String, Object>) weatherRawData.get("main");

		String actualWeather = StringUtils.EMPTY;
		if (weatherMap != null && weatherMap.size() > 0) {
			Map<String, Object> extractedWeatherMap = weatherMap.get(0);
			actualWeather = (String) extractedWeatherMap.get("description");
		}

		String location = (String) weatherRawData.get("name");
		double tempRaw = (double) mainMap.get("temp");
		String temperature = Double.toString(tempRaw);

		Weather weather = new Weather();
		weather.setLocation(location);
		weather.setActualWeather(actualWeather);
		weather.setTemperature(temperature);
		return weather;
	}
}
