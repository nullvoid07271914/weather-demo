package com.src.weather.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.src.weather.models.Weather;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class WeatherRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private WeatherRepositories weatherRepo;

	@Test
	public void whenPersist_ThenReturnWeather() {
		Weather weather = new Weather();
		weather.setResponseId("AFE4HTN3-SEL78G4W-M76G44RQ");
		weather.setLocation("London");
		weather.setTemperature(Double.toString(121.34));
		weather.setActualWeather("rainy");
		weather.setDtimeInserted(new Date());

		Weather savedWeather = entityManager.persist(weather);
		Optional<Weather> fromDbWeather = weatherRepo.findById(savedWeather.getId());

		assertThat(fromDbWeather.get()).isEqualTo(weather);
	}
}
