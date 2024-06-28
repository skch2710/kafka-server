package com.skch.kafka_server;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skch.kafka_server.dto.KafkaMessage;
import com.skch.kafka_server.dto.Student;
import com.skch.kafka_server.util.LocalDateAdapter;
import com.skch.kafka_server.util.LocalDateTimeAdapter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaListeners {

	@KafkaListener(topics = "${app.publishing_topic}", groupId = "my-group")
	public void listener(String message) {
		KafkaMessage kafkaMessage = null;
		String entity = null;
		try {
			GsonBuilder b = new GsonBuilder();
			b.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
			b.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe());

			Gson gson = b.create();
			kafkaMessage = gson.fromJson(message, KafkaMessage.class);

			switch (kafkaMessage.getEntityName()) {
			case "Student":
				entity = "Student";

				Student student = gson.fromJson(message, Student.class);

				log.info("Student From Listener :: " + student);
				break;
			default:
				log.error("Message Not Match....");

			}
		} catch (Exception e) {
			log.error("Error in Listener :: " + message + entity);
		}
	}

}
