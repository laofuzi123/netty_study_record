package com.lc.netty.ex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 自定义的处理器
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端发过来的请求 并返回响应的
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());
        Thread.sleep(10000);
        if(msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            String name = httpRequest.method().name();
            System.out.println("请求方法名:"+name);
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求的是/favicon.ico");
                return;
            }

            // ByteBuf 是netty中非常重要的概念
            // 返回响应内容
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            // 构造返回响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                    HttpResponseStatus.OK, content);
            // 构造返回的头信息
            // 类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            // 响应的信息长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            // 返回
            // 注意这里有两个方法 一个是write 和 writeAndFlush 如果只是单独的调用write并不会真的返回只是放入到缓冲区
            ctx.writeAndFlush(response);
            ctx.channel().closeFuture();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
