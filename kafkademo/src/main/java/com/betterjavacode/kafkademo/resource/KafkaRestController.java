package com.betterjavacode.kafkademo.resource;

import com.betterjavacode.kafkademo.model.Company;
import com.betterjavacode.kafkademo.producers.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/kafka")
public class KafkaRestController
{
    private final KafkaProducer kafkaProducer;

    @Autowired
    public KafkaRestController(KafkaProducer kafkaProducer)
    {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/send", consumes={"application/json"}, produces = {"application/json"})
    public void sendMessageToKafkaTopic(@RequestBody Company company)
    {
        this.kafkaProducer.sendMessage(company);
    }
}
