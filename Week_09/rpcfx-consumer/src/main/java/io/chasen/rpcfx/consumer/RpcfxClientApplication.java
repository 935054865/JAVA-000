package io.chasen.rpcfx.consumer;


import io.chasen.rpcfx.api.Order;
import io.chasen.rpcfx.api.OrderService;
import io.chasen.rpcfx.api.User;
import io.chasen.rpcfx.api.UserService;
import io.chasen.rpcfx.core.client.Rpcfx;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

	// 二方库
	// 三方库 lib
	// nexus, userserivce -> userdao -> user
	//

	public static void main(String[] args) throws Throwable {

		// UserService service = new xxx();
		// service.findById

		UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());

		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

		// 新加一个OrderService

//		SpringApplication.run(RpcfxClientApplication.class, args);
	}

}
