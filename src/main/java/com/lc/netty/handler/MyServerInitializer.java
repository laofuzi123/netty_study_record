package com.lc.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    /*
    客户端一旦连接上 MyServerInitializer对象将被创建 并调用 initChannel方法
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取对应的pipeline 对象
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new MyByteToLongDecoder());
        channelPipeline.addLast(new MyLongToByteEncoder());
        // 最后可以添加自己的处理器
        channelPipeline.addLast("myServerHandler",new MyServerHandler());
    }
}
