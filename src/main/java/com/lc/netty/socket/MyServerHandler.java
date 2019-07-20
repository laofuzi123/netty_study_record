package com.lc.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 自定义的处理器
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /*
    ChannelHandlerContext ctx 上下文信息
    String msg 真正接收到的请求对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取远程的地址
        System.out.println(ctx.channel().remoteAddress()+"，"+msg);
        // 处理请求信心 并返回给客户端的处理应该在 这个方法里面写
        // write 表示将内容写入缓冲区、
        // Flush 表示将缓冲区的内容推出去 并清除缓冲区
        // writeAndFlush 则是上面两个动作的一个合并
        ctx.channel().writeAndFlush("form server ,"+ UUID.randomUUID());
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
