package com.akki.exception;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

@Component
public class CustomErrorHandler implements ConsumerAwareErrorHandler {
    private static final Logger logger = getLogger(CustomErrorHandler.class);

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        logger.error("Exception Thrown", thrownException);
        logger.error("data {}", data);

            consumer.commitAsync();

        logger.info("Acknowledged");
    }
}
