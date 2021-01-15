package com.kafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void simpleProducer(String message) {
        System.out.println("生产-"+message);
        kafkaTemplate.send("kafa-topic", message);
    }
}
