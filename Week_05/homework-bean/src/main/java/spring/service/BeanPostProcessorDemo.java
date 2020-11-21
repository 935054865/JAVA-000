package spring.service;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.bean.User;

public class BeanPostProcessorDemo {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-bean-porcessor.xml");

        User user = ac.getBean("user" ,User.class);

        System.out.println(user.toString());
        ac.registerShutdownHook();
    }

}
