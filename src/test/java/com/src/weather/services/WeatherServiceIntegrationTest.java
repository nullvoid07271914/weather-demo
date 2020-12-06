package com.src.weather.services;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.src.weather.models.Weather;
import com.src.weather.repositories.WeatherRepositories;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class WeatherServiceIntegrationTest {

	@Autowired
	private WeatherService weatherService;

	@MockBean
	private WeatherRepositories weatherRepository;

	@Test
	public void saveWeather_thenReturnWeather() {
		Weather weather = new Weather();
		weather.setResponseId("AFE4HTN3-SEL78G4W-M76G44RQ");
		weather.setLocation("London");
		weather.setTemperature(Double.toString(121.34));
		weather.setActualWeather("rainy");
		weather.setDtimeInserted(new Date());

		Optional<Weather> savedWeather = weatherService.save(weather);

		Long id = null;
		if (savedWeather.isPresent()) {
			Weather fromSavedWeather = savedWeather.get();
			id = fromSavedWeather.getId();
		}

		Mockito.when(weatherRepository.findById(id)).thenReturn(savedWeather);
		Mockito.when(weatherRepository.findById(-99L)).thenReturn(Optional.empty());
	}
}
