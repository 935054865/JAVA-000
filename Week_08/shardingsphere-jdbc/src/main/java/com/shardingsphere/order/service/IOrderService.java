package com.shardingsphere.order.service;
import com.shardingsphere.order.model.MallOrder;

import java.util.List;

public interface IOrderService {
    List<MallOrder> list();

    void insert(MallOrder mallOrder);
}
