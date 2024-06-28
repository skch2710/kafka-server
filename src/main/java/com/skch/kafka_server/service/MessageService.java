package com.skch.kafka_server.service;

import com.skch.kafka_server.dto.KafkaMessage;

public interface MessageService {

	public void publishToTopic(KafkaMessage kafkaMessage);

}
