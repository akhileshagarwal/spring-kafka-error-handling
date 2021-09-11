package com.akki.consumer;

import com.akki.dto.Thing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.akki.constant.ApplicationConstant.GROUP_ID_JSON;
import static com.akki.constant.ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY;
import static com.akki.constant.ApplicationConstant.TOPIC_NAME;

@Component
public class KafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
private ObjectMapper objectMapper = new ObjectMapper();
	@KafkaListener(groupId = GROUP_ID_JSON, topics = TOPIC_NAME, containerFactory = KAFKA_LISTENER_CONTAINER_FACTORY)
	public void receivedMessage(Thing message, Acknowledgment acknowledgment) throws JsonProcessingException {

		int arg = Integer.parseInt("1");
		logger.info("Message Id Received {}", message);
		if(arg>10 && arg<13){
			throw new IllegalStateException("Number greater than 10");
		}
		acknowledgment.acknowledge();
	}
}
