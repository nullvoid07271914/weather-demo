package com.src.weather.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.src.weather.models.Weather;

public abstract class WeatherServiceUtil {

	@SuppressWarnings("unchecked")
	public static Weather createWeatherObject(Map<String, Object> weatherRawData) {
		List<Map<String, Object>> weatherMap = (List<Map<String, Object>>) weatherRawData.get("weather");
		Map<String, Object> mainMap = (Map<String, Object>) weatherRawData.get("main");

		String actualWeather = StringUtils.EMPTY;
		if (weatherMap != null && weatherMap.size() > 0) {
			Map<String, Object> extractedWeatherMap = weatherMap.get(0);
			actualWeather = (String) extractedWeatherMap.get("description");
		}

		String location = (String) weatherRawData.get("name");
		Object object = mainMap.get("temp");

		/* fixed bug: carefully handle temperature from api reponse */
		String temperature = StringUtils.EMPTY;
		if (object instanceof String)
			temperature = (String) object;
		else if (object instanceof Double)
			temperature = Double.toString((Double) object);
		else if (object instanceof Integer)
			temperature = Integer.toString((Integer) object);

		Weather weather = new Weather();
		weather.setLocation(location);
		weather.setActualWeather(actualWeather);
		weather.setTemperature(temperature);
		return weather;
	}
}
