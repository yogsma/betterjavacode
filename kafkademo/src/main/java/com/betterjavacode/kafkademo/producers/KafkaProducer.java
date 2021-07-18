package com.betterjavacode.kafkademo.producers;

import com.betterjavacode.kafkademo.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String topic = "companies";

    @Autowired
    private KafkaTemplate<String, Company> kafkaTemplate;

    public void sendMessage(Company company)
    {
        logger.info(String.format("Outgoing Message - Producing -> %s", company));
        this.kafkaTemplate.send(topic, company);
    }
}
