package src.main.getway.filter;

import io.netty.handler.codec.http.HttpHeaders;

public class NettyFilterHandler {

    public static void headerAddNameHandler(HttpHeaders httpHeaders) {
         httpHeaders.set("nio", "Zhang ran");
    }
}
