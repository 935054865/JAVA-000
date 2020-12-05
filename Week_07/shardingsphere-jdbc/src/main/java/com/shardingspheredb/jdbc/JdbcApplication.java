package com.shardingspheredb.jdbc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
//@MapperScan(basePackages = {"com.shardingspheredb.jdbc.core.dao"})
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}

}
