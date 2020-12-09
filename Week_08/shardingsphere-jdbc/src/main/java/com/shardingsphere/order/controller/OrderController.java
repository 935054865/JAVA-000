package com.shardingsphere.order.controller;

import com.shardingsphere.order.model.MallOrder;
import com.shardingsphere.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<MallOrder> listUser() {

        return orderService.list();
    }
}
