package com.akki.config;

import com.akki.constant.ApplicationConstant;
import com.akki.dto.Thing;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

class KafkaConsumerFactory<T> {

  ConcurrentKafkaListenerContainerFactory<String, T> createConsumer(
      ErrorHandler containerAwareErrorHandler,
      int consumerCount) {

    ConcurrentKafkaListenerContainerFactory<String, T> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());
    factory.setConcurrency(consumerCount);
   // factory.setStatefulRetry(true);
   // factory.setRetryTemplate(retryTemplate());

    factory.setErrorHandler(containerAwareErrorHandler);

    ContainerProperties containerProperties = factory.getContainerProperties();
    containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    containerProperties.setMissingTopicsFatal(false);
    return factory;
  }

  public ConsumerFactory<String, T> consumerFactory() {
    Map<String, Object> configMap = new HashMap<>();
    configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);

    configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    configMap.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    configMap.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
configMap.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Thing.class);
    configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_JSON);

    configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "com.akki.dto");
    return new DefaultKafkaConsumerFactory<>(configMap);
  }
}
