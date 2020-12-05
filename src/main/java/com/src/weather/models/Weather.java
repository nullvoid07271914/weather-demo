package com.src.weather.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "WEATHERLOG")
public class Weather implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "RESPONSEID", nullable = false)
	private String responseId;

	@Column(name = "LOCATION", nullable = false)
	private String location;

	@Column(name = "ACTUALWEATHER", nullable = false)
	private String actualWeather;

	@Column(name = "TEMPERATURE", nullable = false)
	private String temperature;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DTIMEINSERTED", nullable = false)
	private Date dtimeInserted;

	@PrePersist
	protected void onCreate() {
		String responseUUID = java.util.UUID.randomUUID().toString();
		setResponseId(responseUUID);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getActualWeather() {
		return actualWeather;
	}

	public void setActualWeather(String actualWeather) {
		this.actualWeather = actualWeather;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Date getDtimeInserted() {
		return dtimeInserted;
	}

	public void setDtimeInserted(Date dtimeInserted) {
		this.dtimeInserted = dtimeInserted;
	}
}
