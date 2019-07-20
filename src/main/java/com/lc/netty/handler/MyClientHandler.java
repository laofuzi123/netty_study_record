package com.lc.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * 自定义处理器
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    /**
     * 服务器向客户端发送消息时 这个方法将会被触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(msg);
        //ctx.channel().writeAndFlush("from :"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(123456L);
        ctx.writeAndFlush(Unpooled.copiedBuffer("helloworldasdsasadasdasdaa", Charset.forName("utf-8")));
    }

}
