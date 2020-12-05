package com.src.weather.repositories;

import org.springframework.stereotype.Repository;

import com.src.weather.models.Weather;

@Repository
public interface WeatherRepositories extends BaseRepositories<Weather, Long> {

}
