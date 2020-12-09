package com.shardingsphere.order.service.impl;

import com.shardingsphere.order.core.dao.MallOrderMapper;
import com.shardingsphere.order.model.MallOrder;
import com.shardingsphere.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    MallOrderMapper mallOrderMapper;

    @Override
    public List<MallOrder> list() {
        return mallOrderMapper.selectAll();
    }

    @Override
    public void insert(MallOrder mallOrder) {
        mallOrderMapper.insertSelective(mallOrder);
    }
}
