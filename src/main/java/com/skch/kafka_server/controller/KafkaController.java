package com.skch.kafka_server.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skch.kafka_server.dto.Student;
import com.skch.kafka_server.service.MessageService;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping("/send-message")
	public ResponseEntity<?> saveUpdatePayment(@RequestBody Student dto) {
		
		dto.setUuid(UUID.randomUUID());
		dto.setEntityName("Student");
		dto.setStatusCode(HttpStatus.OK.value());
		dto.setDob(LocalDate.now());
		dto.setCreatedDate(LocalDateTime.now());
		
		messageService.publishToTopic(dto);
		
		return ResponseEntity.ok("Sended Message....");
	}

}
