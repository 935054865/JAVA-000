package com.shardingspheredb.jdbc.core.service.impl;

import com.shardingspheredb.jdbc.core.dao.TestMapper;
import com.shardingspheredb.jdbc.core.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    TestMapper testMapper;
}
