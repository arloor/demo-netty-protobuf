package com.arloor.demo.server;

import com.arloor.demo.common.OsHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {
    private static final int port = 7777;
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);
    private static EventLoopGroup bossGroup = OsHelper.buildEventLoopGroup(1);
    private static EventLoopGroup workerGroup = OsHelper.buildEventLoopGroup(0);


    public static void main(String[] args) throws InterruptedException {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 10240);
            b.group(bossGroup, workerGroup)
                    .channel(OsHelper.serverSocketChannelClazz())
                    .childHandler(new NettyServerInitializer());

            Channel sslChannel = b.bind(port).sync().channel();
            log.info("启动netty server");
            sslChannel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
