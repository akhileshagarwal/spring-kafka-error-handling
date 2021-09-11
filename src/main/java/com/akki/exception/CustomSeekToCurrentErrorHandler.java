package com.akki.exception;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;
import java.util.function.BiConsumer;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.springframework.kafka.listener.ListenerUtils.recordToString;

//@Component
public class CustomSeekToCurrentErrorHandler extends SeekToCurrentErrorHandler {

    private static final Logger logger = getLogger(CustomSeekToCurrentErrorHandler.class);

    public CustomSeekToCurrentErrorHandler() {
        super(
                (rec, ex) ->
                        logger.error("Exception for {} {}", recordToString(rec, true), ex.getMessage()),
                new FixedBackOff(0, 0));
    }

    @Override
    public void handle(
            Exception thrownException,
            List<ConsumerRecord<?, ?>> records,
            Consumer<?, ?> consumer,
            MessageListenerContainer container) {
        logger.error("Received an error of size {}", records.size());
        logger.error("Messages {}", records);

        try {
            throw new IllegalStateException("dfsd");
           // setCommitRecovered(true);
            //super.handle(thrownException, records, consumer, container);
        }catch (RuntimeException e){
            logger.error(e);
        }
    }
}
