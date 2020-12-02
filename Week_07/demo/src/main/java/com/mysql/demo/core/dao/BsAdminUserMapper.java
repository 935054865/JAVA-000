package com.mysql.demo.core.dao;

import com.mysql.demo.common.model.BsAdminUser;

public interface BsAdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BsAdminUser record);

    int insertSelective(BsAdminUser record);

    BsAdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsAdminUser record);

    int updateByPrimaryKey(BsAdminUser record);
}