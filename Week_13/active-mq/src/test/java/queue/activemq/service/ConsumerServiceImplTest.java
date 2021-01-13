package queue.activemq.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import queue.activemq.ActiveApplication;

import javax.jms.Queue;
import javax.jms.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ActiveApplication.class)
//@TestPropertySource("classpath:application.properties")
class ConsumerServiceImplTest {

    @Autowired
    ConsumerServiceImpl consumerService;

    @Autowired
    ProducerServiceImpl producerService;

    @Autowired
    Queue queue;

    @Autowired
    Topic topic;

    @Test
    void readActiveQueue() {
        int number = 100;
//        System.out.println(queue);

        for (int i = 0; i < number; i++) {
            System.out.println("写入=测试"+i);
            producerService.sendMessage(queue, "测试"+i);
        }
//        consumerService.readActiveQueue();
    }

    @Test
    void readActiveTopic() {
        int number = 100;
//        System.out.println(queue);

        for (int i = 0; i < number; i++) {
            System.out.println("写入=topic-测试"+i);
            producerService.sendMessage(topic, "测试"+i);
        }
//        consumerService.readActiveQueue();
    }

}