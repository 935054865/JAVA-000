package com.kafka.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerServiceImpl {


    @KafkaListener(topics = {"kafa-topic"})
    public void simpleConsumer(ConsumerRecord<?, ?> record) {
        log.info("简单消费-Topic={}, Partition={},Message={}"
        , record.topic(), record.partition(), record.value());
    }
}
