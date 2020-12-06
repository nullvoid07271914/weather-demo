package com.src.weather.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.weather.WeatherApplication;
import com.src.weather.controllers.requestbody.WeatherLocation;
import com.src.weather.models.Weather;
import com.src.weather.repositories.WeatherRepositories;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = WeatherApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WeatherControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WeatherRepositories repo;

	@Test
	public void weatherApi_thenSave() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		WeatherLocation location = new WeatherLocation();
		location.setLocation("London");

		byte[] bytes = mapper.writeValueAsBytes(location);
		mvc.perform(post("/api/weather/by-location").contentType(MediaType.APPLICATION_JSON).content(bytes));

		List<Weather> allWeather = repo.findAll();

		assertThat(allWeather).extracting(Weather::getLocation).containsOnly("London");
	}
}
