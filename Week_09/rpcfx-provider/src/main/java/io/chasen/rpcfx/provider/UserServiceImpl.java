package io.chasen.rpcfx.provider;

import io.chasen.rpcfx.api.User;
import io.chasen.rpcfx.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
