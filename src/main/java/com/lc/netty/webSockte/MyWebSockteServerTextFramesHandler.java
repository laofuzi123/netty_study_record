package com.lc.netty.webSockte;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 需要注意 这里由于是处理 TextFrame  所以泛型中需要加入 TextWebSocketFrame
 */
public class MyWebSockteServerTextFramesHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到的消息:"+msg.text());

        // 注意由于我们传递的是TextWebSocketFrame 如果用普通的字符串 handler是无法解析的
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:"+ LocalDate.now()));
    }

    /**
     * 连接建立的触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded:"+ctx.channel().id().asLongText());
    }

    /**
     * 连接关闭 触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved:"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("异常发生");
        ctx.close();
    }
}
