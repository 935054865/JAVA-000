package com.shardingspheredb.jdbc.controller;

import com.shardingspheredb.jdbc.core.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ITestService testService;

//    /**
//     * @Description: 批量保存用户
//     */
//    @PostMapping("save-user")
//    public Object saveTest() {
//        return testService.insert();
//    }
    /**
     * @Description: 获取用户列表
     */
    @GetMapping("list-user")
    public Object listTest() {
        return testService.list();
    }

}
