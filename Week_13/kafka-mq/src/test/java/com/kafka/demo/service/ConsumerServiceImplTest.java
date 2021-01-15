package com.kafka.demo.service;

import com.kafka.demo.KafkaApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
class ConsumerServiceImplTest {

    @Autowired
    ProducerServiceImpl producerService;

    @Autowired
    ConsumerServiceImpl consumerService;

    @Test
    public void simpleProducer() {
        int number = 100;
        for (int i = 0; i < number; i++) {
            producerService.simpleProducer("测试-" + i);
        }

    }
}