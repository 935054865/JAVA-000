package src.main.getway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

public class HelloWorldOutboundHandler {

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
        try {
            FullHttpResponse response = toResponse("hello world", "");
            ctx.writeAndFlush(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.flush();
            ctx.close();
        }


    }

    /**
     * 返回固定信息
     * @param out
     * @param conType
     * @return
     */
    public static FullHttpResponse toResponse(String out, String conType){
        //这只HTTP及请求头信息
        FullHttpResponse response = new DefaultFullHttpResponse(
                // 设置版本为HTTP 1.1
                HttpVersion.HTTP_1_1,
                // 设置相应状态码
                HttpResponseStatus.FORBIDDEN,
                Unpooled.wrappedBuffer(out.getBytes(Charset.defaultCharset()))
        );
        if (!"".equals(conType) && conType != null) {
            response.headers().set("Content-Type", conType);
        }

        return response;
    }
}
