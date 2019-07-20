package com.lc.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyProtoBufServerHandler extends SimpleChannelInboundHandler<MyMessage.MyMessageInfo> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage.MyMessageInfo msg) throws Exception {
        System.out.println("收到客户端的消息:");
        switch (msg.getDateType()){
            case Cat:
                System.out.println(msg.getCat().getAge());
                System.out.println(msg.getCat().getName());

                break;
            case Dog:
                System.out.println(msg.getDog().getAge());
                System.out.println(msg.getDog().getName());
                break;
            case Person:
                System.out.println(msg.getPerson().getAddress());
                System.out.println(msg.getPerson().getAge());
                System.out.println(msg.getPerson().getName());
                break;
            default:
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
