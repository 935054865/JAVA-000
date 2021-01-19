package io.chasen.zmq.core.Demo;

import io.chasen.zmq.core.Service.ZmqBroker;
import io.chasen.zmq.core.Service.ZmqConsumer;
import io.chasen.zmq.core.Service.ZmqMessage;
import io.chasen.zmq.core.Service.ZmqProducer;
import lombok.SneakyThrows;

public class ZmpDemo {
    private static String TOPIC = "test";
    public static void main(String[] args) {

        ZmqBroker zmqBroker = new ZmqBroker();
        zmqBroker.createTopic(TOPIC);
        producer(zmqBroker);
        consumer(zmqBroker);
    }


    private static void consumer(ZmqBroker zmqBroker) {
        ZmqConsumer consumer = zmqBroker.createConsumer();
        consumer.subscribe(TOPIC);
        new Thread(() -> {
            while (true) {
                ZmqMessage message = consumer.poll();
                if(null != message) {
                    System.out.println(message.getBody());
                } else {
                    System.out.println("消息内容为null");
                }
            }
        }).start();
    }


    @SneakyThrows
    private static void producer(ZmqBroker zmqBroker) {
        ZmqProducer producer = zmqBroker.createProducer();
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                producer.send(TOPIC, new ZmqMessage(null, "order_" + i));
            }
        }).start();
        Thread.sleep(500);
    }
}
