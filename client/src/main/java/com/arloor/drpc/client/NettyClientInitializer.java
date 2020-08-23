package com.arloor.drpc.client;

import domain.employee.Employee;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        ProtobufEncoder protobufEncoder = new ProtobufEncoder();
        pipeline.addLast(protobufEncoder);
        pipeline.addLast(new SimpleChannelInboundHandler<SocketChannel>() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ctx.channel().writeAndFlush(Employee.newBuilder().setId(1).setName("aa").build());
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketChannel socketChannel) throws Exception {

            }
        });

    }
}
