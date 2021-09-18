package com.betterjavacode.messagereceiver.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class MessageConsumer
{
    private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "demo-queue")
    public void receiveMessage(String message)
    {
        // TO -DO
        logger.info("Received a message = {}", message);
    }
}
