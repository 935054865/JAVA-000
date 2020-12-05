package com.shardingspheredb.jdbc.core.service.impl;

import com.shardingspheredb.jdbc.core.dao.TestMapper;
import com.shardingspheredb.jdbc.core.service.ITestService;
import com.shardingspheredb.jdbc.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public List<Test> list() {
        return testMapper.selectAll();
    }
}
