package com.shardingspheredb.jdbc.core.dao;

import com.shardingspheredb.jdbc.model.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    List<Test> selectAll();
}