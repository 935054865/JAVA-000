package io.chasen.rpcfx.core.api;

import lombok.Data;

@Data
public class RpcfxResponse {

    private Integer code;

    private Object result;

    private String msg;

    private Exception exception;
}
