package io.chasen.rpcfx.core.client.proxy;

import java.lang.reflect.InvocationHandler;

public class JavassistProxyFactory implements IProxyFactory {

    @Override
    public <T> T getProxy(Object target, InvocationHandler handler) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass(), handler);
    }
}
