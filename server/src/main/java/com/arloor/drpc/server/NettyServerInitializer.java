package com.arloor.drpc.server;

import domain.employee.Employee;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;


public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ProtobufDecoder(Employee.getDefaultInstance()));
        pipeline.addLast(new SimpleChannelInboundHandler<Employee>() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Employee employee) throws Exception {
                System.out.println(employee);
            }
        });
    }
}
