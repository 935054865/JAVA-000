package io.chasen.rpcfx.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.chasen.rpcfx.core.api.IRpcfxResolver;
import io.chasen.rpcfx.core.api.RpcfxRequest;
import io.chasen.rpcfx.core.api.RpcfxResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcfxInvoker {

    IRpcfxResolver rpcfxResolver;

    public RpcfxInvoker(IRpcfxResolver resolver){
        this.rpcfxResolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        Object service = rpcfxResolver.resolve(serviceClass);

        try {
            Method method = service.getClass().getMethod(request.getMethod(), int.class);
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setCode(0);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setCode(10000);
            return response;
        }
    }

}
