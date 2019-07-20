package com.lc.netty.ex;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {


    public static void main(String[] args)  throws Exception{
        // 定义事件循环组
        // bossGroup 只是做一个接收动作 并不进行真正的处理 会将请求转发给workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 用于启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    // 定义通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器 我们自己所编写的处理器
                    .childHandler(new TestServerInit());

            // 启动绑定
            ChannelFuture sync =  serverBootstrap.bind(8899).sync();
            // 关闭监听
            sync.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
