package com.akki.config;

import java.util.HashMap;
import java.util.Map;

import com.akki.dto.Thing;
import com.akki.exception.CustomSeekToCurrentErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.akki.constant.ApplicationConstant;

@Configuration
public class SpringKafkaConfig {
	/**
	 private final SeekToCurrentErrorHandler customSeekToCurrentErrorHandler;

	 @Autowired
	 public SpringKafkaConfig(SeekToCurrentErrorHandler customSeekToCurrentErrorHandler) {
	 this.customSeekToCurrentErrorHandler = customSeekToCurrentErrorHandler;
	 }
	 */

	private final ErrorHandler customErrorHandler;

	@Autowired
	public SpringKafkaConfig(ErrorHandler customErrorHandler) {
		this.customErrorHandler = customErrorHandler;
	}

	@Bean
	public ProducerFactory<String, Thing> producerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configMap);
	}

	@Bean
	public KafkaTemplate<String, Thing> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}



	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

		return new KafkaConsumerFactory<String>().createConsumer(customErrorHandler, 3);
	}
}
