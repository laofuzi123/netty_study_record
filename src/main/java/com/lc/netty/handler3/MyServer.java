package com.lc.netty.handler3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务器端
 */
public class MyServer {

    public static void main(String[] args)  throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 这里为什么不适用 handler() 而使用 childHandler
            // 因为 handler主要是针对 bossGroup  childHandler 针对的是 workerGroup
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new MyServerInitializer());
            // sync() 这里是必须要加的 表示netty在这里会一直等待
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
