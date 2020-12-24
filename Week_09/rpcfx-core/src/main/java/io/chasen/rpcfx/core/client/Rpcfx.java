package io.chasen.rpcfx.core.client;


import com.alibaba.fastjson.parser.ParserConfig;
import io.chasen.rpcfx.core.client.proxy.JavassistProxyFactory;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

import java.lang.reflect.Proxy;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.chasen");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) throws Throwable {

        // 0. 替换动态代理 -> AOP
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
//        return new JavassistProxyFactory().getProxy(new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
        return (T) new ByteBuddy().subclass(Object.class)
                .implement(serviceClass)
                .intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass, url)))
                .make()
                .load(serviceClass.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded()
                .newInstance();
    }

}