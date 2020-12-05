package com.src.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.src.weather.models.WeatherUrl;

@Configuration
@ComponentScan(basePackages = { "com.src.weather" })
@PropertySource("classpath:weather.properties")

public class WeatherConfig {

	@Value("${weather.url}")
	private String url;

	@Value("${weather.apikey}")
	private String apiKey;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
	}

	@Bean
	public WeatherUrl weatherUrl() {
		return new WeatherUrl(url, apiKey);
	}
}
