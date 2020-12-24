package io.chasen.rpcfx.provider;

import io.chasen.rpcfx.api.OrderService;
import io.chasen.rpcfx.api.UserService;
import io.chasen.rpcfx.core.api.IRpcfxResolver;
import io.chasen.rpcfx.core.api.RpcfxRequest;
import io.chasen.rpcfx.core.api.RpcfxResponse;
import io.chasen.rpcfx.core.service.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcfxServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcfxServerApplication.class, args);
	}

	@Autowired
	RpcfxInvoker invoker;

	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
		return invoker.invoke(request);
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired IRpcfxResolver resolver){
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public IRpcfxResolver createResolver(){
		return new DemoResolver();
	}

	// 能否去掉name
	//
	@Bean(name = "io.chasen.rpcfx.api.UserService")
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean(name = "io.chasen.rpcfx.api.OrderService")
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
