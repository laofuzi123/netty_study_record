package com.lc.netty.talkHouse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyServerChatHandler extends SimpleChannelInboundHandler<String> {

    // 需要将已经建立连接的 channel保存起来 这里采用netty 提供的 channelGroup
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     * 服务器端 收到客户端任何一个消息时  这里得到调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            if(channel != ch) {
                ch.writeAndFlush(channel.remoteAddress()+",发送的消息:"+msg+"\n");
            } else {
                ch.writeAndFlush("[自己]："+msg+"\n");
            }
        });
    }

    /**
     * 表示 服务端与客户端建立连接 触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 获取 channel对象 这里的channel可以看成一个connection
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】："+channel.remoteAddress()+"-加入\n");

        channelGroup.add(channel);
    }

    /**
     * 客户端与服务器连接断开 触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.remove(channel); // 这里可写可不写 因为netty中 channel端口 会自动从 channelGroup中将它移除
        channelGroup.writeAndFlush("【服务器】："+channel.remoteAddress()+"-断开连接\n");
    }

    /**
     * 表示连接处于活动状态 触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 这表示在服务端 打印某某上线了
        System.out.println(channel.remoteAddress()+"上线");
    }

    /**
     * 表示连接不处于 活动状态 触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 这表示在服务端 打印某某上线了
        System.out.println(channel.remoteAddress()+"下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
