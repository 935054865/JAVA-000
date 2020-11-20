package spring.beanFactory;

import spring.bean.User;

public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }

    void doDestroy();
}
