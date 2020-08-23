package com.arloor.demo.client;

import domain.demo.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import java.util.concurrent.CompletableFuture;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    CompletableFuture completableFuture;

    private static final int count = 10;

    public NettyClientInitializer(CompletableFuture complete) {
        this.completableFuture = complete;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //非必须
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        ProtobufEncoder protobufEncoder = new ProtobufEncoder();
        pipeline.addLast(protobufEncoder);
        pipeline.addLast(new SimpleChannelInboundHandler<SocketChannel>() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                for (int i = 0; i < count; i++) {
                    int yu = i % 2;
                    if (yu == 0) {
                        ctx.channel().writeAndFlush(DataInfo.Model.newBuilder()
                                .setDataType(DataInfo.Model.ModelType.WorkerType)
                                .setWorker(DataInfo.Worker.newBuilder().setAge(10).setName("worker").build())
                                .build()).addListener(future -> {
                            System.out.println(future.isSuccess());
                        });
                    } else {
                        ctx.channel().writeAndFlush(DataInfo.Model.newBuilder()
                                .setDataType(DataInfo.Model.ModelType.StudentType)
                                .setStudent(DataInfo.Student.newBuilder().setEmail("admin@arloor.com").setName("student").build())
                                .build()).addListener(future -> {
                            System.out.println(future.isSuccess());
                        });
                    }
                    Thread.sleep(1000);
                }
                ctx.channel().close();
                completableFuture.complete(null);
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketChannel socketChannel) throws Exception {

            }
        });

    }
}
