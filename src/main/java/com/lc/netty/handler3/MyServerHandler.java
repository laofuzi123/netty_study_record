package com.lc.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {

        int length = msg.getLength();
        byte[] bytes = msg.getContext();

        System.out.println("服务端接收到的数据:");
        System.out.println("消息长度:"+length);
        System.out.println("消息内容:"+new String(bytes, Charset.forName("utf-8")));
        System.out.println("接收到的消息数量:"+(++this.count));

        // 返回给客户端一个数据
        String responseMessage = UUID.randomUUID().toString();
        // 这里还不能直接length获取长度
        int responseLength = responseMessage.getBytes("utf-8").length;
        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseLength);
        personProtocol.setContext(responseMessage.getBytes("utf-8"));
        ctx.writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
