package queue.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication(scanBasePackages = "queue.activemq")
public class ActiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveApplication.class, args);
    }

}
