package com.skch.kafka_server.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skch.kafka_server.dto.KafkaMessage;
import com.skch.kafka_server.service.MessageService;
import com.skch.kafka_server.util.DateAdapter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

	@Value("${app.publishing_topic}")
	private String PUBLISHING_TOPIC;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void publishToTopic(KafkaMessage kafkaMessage) {

		log.info("Starting Publishing to Topic......");

		try {
			GsonBuilder b = new GsonBuilder();
			b.registerTypeAdapter(LocalDate.class, new DateAdapter<LocalDate>().nullSafe());
	        b.registerTypeAdapter(LocalDateTime.class, new DateAdapter<LocalDateTime>().nullSafe());

			Gson gson = b.create();
			String message = gson.toJson(kafkaMessage);

			log.info("Message :: " + message);
			log.info("Topic ::" + PUBLISHING_TOPIC);

			CompletableFuture<SendResult<String, String>> sendFuture = kafkaTemplate.send(PUBLISHING_TOPIC, message);

			sendFuture.thenAccept(sendResult -> {
				// Handle success
				log.info("Message sent successfully: " + sendResult);
			}).exceptionally(throwable -> {
				// Handle failure
				log.error("Failed to send message: " + throwable.getMessage());
				return null;
			});
		} catch (Exception e) {
			log.error("Failed to send message: " + e);
		}

	}

}
