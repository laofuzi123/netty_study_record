package com.lc.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PersonProtocol personProtocol;
        for (int i=0;i<10;i++) {
            personProtocol = new PersonProtocol();
            byte[] bytes = "send message!".getBytes("utf-8");
            int length = bytes.length;
            personProtocol.setLength(length);
            personProtocol.setContext(bytes);
            ctx.writeAndFlush(personProtocol);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        System.out.println("客户端接收到消息");
        System.out.println("长度:"+msg.getLength());
        System.out.println("内容:"+new String(msg.getContext(), Charset.forName("utf-8")));
        System.out.println("接收到消息数量:"+(++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
