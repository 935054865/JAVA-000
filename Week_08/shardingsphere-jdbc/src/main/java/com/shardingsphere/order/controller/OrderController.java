package com.shardingsphere.order.controller;

import com.shardingsphere.order.common.util.SnowflakeIdGenerator;
import com.shardingsphere.order.model.MallOrder;
import com.shardingsphere.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    /**
     * @Description: 获取用户列表
     * @return
     */
    @GetMapping("/list")
    public List<MallOrder> listOrder() {

        return orderService.list();
    }

    /**
     * @Description: 获取用户列表
     * @return
     */
    @PostMapping("/add")
    public String addOrder() {
        MallOrder mallOrder = new MallOrder();
        mallOrder.setId(SnowflakeIdGenerator.generateId());
        mallOrder.setOrderCode(SnowflakeIdGenerator.generateId().toString());
        mallOrder.setUserId(SnowflakeIdGenerator.generateId());
        mallOrder.setShopId((int) (Math.random() * 100));
        mallOrder.setPayType((byte) 0);
        mallOrder.setUserAddressId((int) (Math.random() * 100));
        mallOrder.setSpuSnapshotId((int) (Math.random() * 100));
        mallOrder.setOrderRefundPrice(new BigDecimal(0));
        mallOrder.setPayAmount(new BigDecimal(1));
        mallOrder.setRealPay(new BigDecimal(1));
        orderService.insert(mallOrder);
        return  "成功";
    }
}
