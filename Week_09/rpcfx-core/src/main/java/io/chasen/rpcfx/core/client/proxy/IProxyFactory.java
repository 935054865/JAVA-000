package io.chasen.rpcfx.core.client.proxy;

import java.lang.reflect.InvocationHandler;

public interface IProxyFactory {

    <T> T getProxy(Object target, InvocationHandler handler) throws Throwable;
}
