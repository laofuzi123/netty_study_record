package com.lc.netty.talkHouse;

import com.lc.netty.socket.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 简易聊天 客户端
 */
public class MyChatClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            // 客户端的连接
            Bootstrap bootstrap = new Bootstrap();
            // 注意这里和服务端 有点不一样 这里是 NioSocketChannel
            // 客户端使用的是 handler() 而非服务器端的 childHandler()
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatClientInitializer());

            // 进行连接
            Channel channel = bootstrap.connect("localhost",8899).sync().channel();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            for(;;){
                channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
            }


        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
