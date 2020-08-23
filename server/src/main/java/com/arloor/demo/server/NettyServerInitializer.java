package com.arloor.demo.server;

import domain.demo.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //非必须
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufDecoder(DataInfo.Model.getDefaultInstance()));
        pipeline.addLast(new SimpleChannelInboundHandler<DataInfo.Model>() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.Model model) throws Exception {

                switch (model.getDataType()) {
                    case WorkerType:
                        DataInfo.Worker worker = model.getWorker();
                        System.out.println(model.getDataType().toString() + " name: " + worker.getName() + "; age: " + worker.getAge());
                        break;
                    case StudentType:
                        DataInfo.Student student = model.getStudent();
                        System.out.println(model.getDataType().toString() + " name: " + student.getName() + "; email: " + student.getEmail());
                        break;
                }
            }
        });
    }
}
