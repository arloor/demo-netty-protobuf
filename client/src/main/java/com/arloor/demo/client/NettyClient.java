package com.arloor.demo.client;

import com.arloor.demo.common.OsHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class NettyClient {
    private static final int port = 7777;
    private static final Logger log = LoggerFactory.getLogger(NettyClient.class);
    private static EventLoopGroup bossGroup = OsHelper.buildEventLoopGroup(1);
    private static EventLoopGroup workerGroup = OsHelper.buildEventLoopGroup(0);

    public static void main(String[] args) {
        Bootstrap b = new Bootstrap();
        CompletableFuture<Void> complete = new CompletableFuture<>();
        complete.thenAccept((cell) -> {
            System.out.println("优雅关闭");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });
        b.group(workerGroup)
                .channel(OsHelper.socketChannelClazz())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new NettyClientInitializer(complete));

        b.connect("localhost", port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("connect success");
            }
        });
    }
}
