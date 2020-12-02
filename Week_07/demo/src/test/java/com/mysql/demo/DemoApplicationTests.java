package com.mysql.demo;

import com.mysql.demo.core.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	AdminServiceImpl adminService;

	@Test
	void contextLoads() {
	}

	@Test
	void testMaster() {
		adminService.selectById(8);
	}

	@Test
	void testSalve() {
		adminService.selectByIdSalve(8);
	}
}
