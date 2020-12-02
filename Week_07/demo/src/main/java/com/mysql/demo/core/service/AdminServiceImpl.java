package com.mysql.demo.core.service;

import com.mysql.demo.common.DbType;
import com.mysql.demo.common.model.BsAdminUser;
import com.mysql.demo.config.MybatisConfig;
import com.mysql.demo.core.dao.BsAdminUserMapper;
import com.mysql.demo.handler.DbContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    BsAdminUserMapper bsAdminUserMapper;


    @DbType
    public BsAdminUser selectById(int id) {
        BsAdminUser bsAdminUser = bsAdminUserMapper.selectByPrimaryKey(id);
        return bsAdminUser;
    }


    @DbType(isMaster = false)
    public BsAdminUser selectByIdSalve(int id) {
        BsAdminUser bsAdminUser = bsAdminUserMapper.selectByPrimaryKey(id);
        return bsAdminUser;
    }
}
