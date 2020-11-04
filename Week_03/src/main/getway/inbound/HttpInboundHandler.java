package src.main.getway.inbound;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.main.getway.outbound.HelloWorldOutboundHandler;
import src.main.getway.outbound.Netty.NettyHttpClient;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private HelloWorldOutboundHandler httpOutboundHandler;

    public HttpInboundHandler() {

//        httpOutboundHandler = new HelloWorldOutboundHandler();
//        nettyHttpClient = new NettyHttpClient();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //hello world
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
             //固定返回hello world
//            httpOutboundHandler.handle(fullRequest, ctx);
            NettyHttpClient.start(ctx, fullRequest);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
