package com.skch.kafka_server.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private int statusCode;

	@JsonIgnore
	private UUID uuid;

	@JsonIgnore
	private String errorMessage;

	@JsonIgnore
	private String entityName;

	@JsonIgnore
	private String dto;

}
