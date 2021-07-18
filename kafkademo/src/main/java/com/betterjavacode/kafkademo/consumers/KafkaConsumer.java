package com.betterjavacode.kafkademo.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer
{
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "companies", groupId = "group_id")
    public void consume(String company)
    {
        logger.info(String.format("Incoming Message - Consuming -> %s", company));
    }
}
