package com.src.weather.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.weather.controllers.requestbody.WeatherLocation;
import com.src.weather.models.Weather;
import com.src.weather.models.WeatherUrl;
import com.src.weather.services.WeatherService;

@RestController
@RequestMapping("/api")
public class WeatherController {
	
	private static final String HOST = "api.openweathermap.org";
	
	private static final String PATH = "/data/2.5/weather";
	
	private static final String QUERY = "q={keyword}&appid={appid}";

	@Autowired
	private RestTemplate rest;

	@Autowired
	private WeatherUrl weatherApiData;

	@Autowired
	private WeatherService service;

	@SuppressWarnings("unchecked")
	@PostMapping(path = "/weather/by-location",
		consumes = { MediaType.APPLICATION_JSON_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getWeatherByLocation(@RequestBody WeatherLocation requestBody)
			throws JsonMappingException, JsonProcessingException {
		String location = requestBody.getLocation();

		Map<String, String> param = new HashMap<>();
		param.put("keyword", location);
		param.put("appid", weatherApiData.getApiKey());

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host(HOST)
				.path(PATH)
				.query(QUERY)
				.buildAndExpand(param);

		String uri = uriComponents.toUriString();
		ResponseEntity<String> res = rest.exchange(uri, HttpMethod.GET, null, String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> weatherRawData = mapper.readValue(res.getBody(), Map.class);

		Weather weatherPersist = service.createWeatherObject(weatherRawData);
		Optional<Weather> weather = service.save(weatherPersist);
		return ResponseEntity.ok(weather.get());
	}
}
