package io.chasen.rpcfx.provider;

import io.chasen.rpcfx.api.Order;
import io.chasen.rpcfx.api.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
