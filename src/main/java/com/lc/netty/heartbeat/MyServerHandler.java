package com.lc.netty.heartbeat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 这个handler和前面所写的有区别
 * 前面我们继承的是SimpleChannelInboundHandler
 * 这里需要继承 ChannelInboundHandlerAdapter 适配器
 */
public class MyServerHandler  extends ChannelInboundHandlerAdapter {


    /**
     * 事件触发 事件
     * 作用：用来将触发的事件 转发到下一个处理的handler
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 空闲状态 检测事件IdleStateEvent
        if( evt instanceof IdleStateEvent) {
            IdleStateEvent envent = (IdleStateEvent)evt;
            String eventType = null;
            switch (envent.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress()+":空闲状态:"+eventType);
            ctx.channel().close();

        }
    }
}
