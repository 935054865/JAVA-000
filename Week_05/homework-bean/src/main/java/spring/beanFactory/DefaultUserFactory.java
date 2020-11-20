package spring.beanFactory;

import org.springframework.beans.factory.InitializingBean;
import spring.bean.User;

public class DefaultUserFactory implements UserFactory , InitializingBean {

//    @PostConstruct
    public void init() {
        System.out.println("UserFactory init...");
    }

    @Override
    public void doDestroy() {
        System.out.println("customers destroy.....");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet(), init ...");
    }
}
