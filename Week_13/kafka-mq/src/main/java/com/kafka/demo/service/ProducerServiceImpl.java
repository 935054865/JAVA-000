package com.kafka.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Slf4j
public class ProducerServiceImpl {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void simpleProducer(String message) {
        System.out.println("生产-"+message);
        kafkaTemplate.send("kafa-topic", message);
    }

    public void sendMessageCallbackMessage(String callbackMessage) {
        kafkaTemplate.send("kafa-topic", callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            log.info("发送消息成功:topic={}, partition={}, offset={}", topic, partition, offset);
        }, failure -> {
            log.error("发送消息失败={}", failure.getMessage());
        });
    }

    public void sendMessageCallbackMessag2(String callbackMessage) {
        kafkaTemplate.send("kafa-topic", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });
    }

}
