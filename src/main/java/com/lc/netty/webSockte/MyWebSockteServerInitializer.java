package com.lc.netty.webSockte;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyWebSockteServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 这里因为webSockte 本身也是基于http 所以需要加上http的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // http 聚合处理器 netty对http请求采取分段的方式（特别重要的处理器）
        pipeline.addLast(new HttpObjectAggregator(8192));
        // 处理http webSockte处理器（用于处理webSockte的握手以及processing of control frames (Close, Ping, Pong)）
        // 注意这里的数据是以 frames 方式来传递
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new MyWebSockteServerTextFramesHandler());
    }
}
