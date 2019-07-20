package com.lc.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 自定义的处理器
 */
public class MyServerHandler2 extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;
    /*
    ChannelHandlerContext ctx 上下文信息
    String msg 真正接收到的请求对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 服务端接收到消息
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收到的消息内容:"+message);
        System.out.println("服务端接收到的消息数量:"+(++this.count));
        ByteBuf byteBuf = Unpooled.copiedBuffer("form server ,"+ UUID.randomUUID(),Charset.forName("utf-8"));
        ctx.channel().writeAndFlush(byteBuf);
    }


    /*
    出现异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 出现异常关闭连接
        ctx.channel().close();
    }


}
