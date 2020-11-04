package src.main.getway.outbound.Netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import src.main.getway.config.GetAwayConfig;

import java.net.InetSocketAddress;

public class NettyHttpClient {


    public static void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(eventLoopGroup)
                    .remoteAddress(new InetSocketAddress(GetAwayConfig.PROXY_SERVER, GetAwayConfig.PROXY_SERVER_PORT))
                    //长连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel channel) throws Exception {

                            channel.pipeline().addLast(new HttpClientCodec());

                            channel.pipeline().addLast(new HttpObjectAggregator(1024 * 10 * 1024));

                            channel.pipeline().addLast(new HttpContentDecompressor());

                            channel.pipeline().addLast(new NettyOutboundHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
