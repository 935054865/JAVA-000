package test.spring.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import test.spring.bean.User;



public class BeanAnnotationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanAnnotationDemo.class);

        applicationContext.refresh();

        User user = applicationContext.getBean("userBean", User.class);

        System.out.println(user.toString());

        applicationContext.close();
    }

    // 1、通过bean方式
    @Bean(name = "userBean")
    public User getUser() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("lcr");
        return user;
    }

}
