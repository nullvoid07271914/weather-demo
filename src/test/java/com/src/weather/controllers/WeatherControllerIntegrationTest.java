package com.src.weather.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
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
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
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

	private byte[] requestBody;

	@Before
	public void setup() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		WeatherLocation location = new WeatherLocation();
		location.setLocation("London");
		requestBody = mapper.writeValueAsBytes(location);
	}

	@Test
	public void weatherApiThenSave() throws Exception {
		mvc.perform(post("/api/weather/by-location")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody));
		List<Weather> allWeather = repo.findAll();
		assertThat(allWeather).extracting(Weather::getLocation).contains("London");
	}

	@Test
	public void weatherApiUnsupportedMediaType() throws Exception {
		ResultActions result = mvc
				.perform(post("/api/weather/by-location")
				.contentType(MediaType.TEXT_XML_VALUE)
				.content(requestBody));
		
		result.andDo(print());
		result.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void weatherApiMethodNotAllowed() throws Exception {
		ResultActions result = mvc.perform(get("/api/weather/by-location").contentType(MediaType.APPLICATION_JSON));
		result.andDo(print());
		result.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void weatherApiNotFound() throws Exception {
		ResultActions result = mvc
				.perform(post("/api/weather/not-found")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody));
		
		result.andDo(print());
		result.andExpect(status().isNotFound());
	}

	@Test
	public void weatherApiBadRequest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		WeatherLocation location = new WeatherLocation();
		location.setLocation("undefined");
		byte[] bytes = mapper.writeValueAsBytes(location);

		ResultActions result = mvc
				.perform(post("/api/weather/by-location")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bytes));
		
		result.andDo(print());
		result.andExpect(status().isInternalServerError());
	}
}
