package com.lc.netty.handler2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取对应的pipeline 对象
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new MyClientHandler());
    }


}
