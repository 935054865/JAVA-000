package src.main.getway.outbound.Netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import src.main.getway.filter.NettyFilterHandler;

import java.net.URI;
import java.nio.charset.Charset;

public class NettyOutboundHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext serverCtx;
    private FullHttpRequest serverRequest;

    public NettyOutboundHandler(ChannelHandlerContext serverCtx, FullHttpRequest fullRequest) {
        this.serverCtx = serverCtx;
        this.serverRequest = fullRequest;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI url = new URI(serverRequest.uri());
        FullHttpRequest request = null;
        if ("POST".equals(serverRequest.method().name())) {
            request = postRequest(url);
        } else {
            request = getRequest(url);
        }
        HttpHeaders httpHeaders = request.headers();
        headerHandle(httpHeaders, request);
        filterHandler(httpHeaders);
        //发送数据
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpResponse response = (FullHttpResponse) msg;

            ByteBuf content = response.content();
//            HttpHeaders headers = response.headers();


            System.out.println(content.toString(CharsetUtil.UTF_8));
            serverCtx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }

    }

    /**
     * header处理
     * @param httpHeaders
     * @param request
     */
    private void headerHandle(HttpHeaders httpHeaders, FullHttpRequest request) {
        //开启长连接
        httpHeaders.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        //设置传递请求内容的长度
        httpHeaders.set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        serverRequest.headers().forEach((header)-> httpHeaders.set(header.getKey(), header.getValue()));
    }

    /**
     * header过滤
     * @param httpHeaders
     */
    private void filterHandler(HttpHeaders httpHeaders) {
        NettyFilterHandler.headerAddNameHandler(httpHeaders);
    }


    /**
     * 获取Get请求
     * @param url
     * @return
     */
    private FullHttpRequest getRequest(URI url) {
        //配置HttpRequest的请求数据和一些配置信息
        //                , Unpooled.wrappedBuffer(meg.getBytes(Charset.defaultCharset())));
        return new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_0
                , HttpMethod.GET
                , url.toASCIIString());
    }

    /**
     * 获取post请求
     * @param url
     * @return
     */
    private FullHttpRequest postRequest(URI url) {
        //配置HttpRequest的请求数据和一些配置信息
        //                , Unpooled.wrappedBuffer(meg.getBytes(Charset.defaultCharset())));
        return new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_0
                , HttpMethod.POST
                , url.toASCIIString()
                , Unpooled.wrappedBuffer(serverRequest.content()));
    }
}
