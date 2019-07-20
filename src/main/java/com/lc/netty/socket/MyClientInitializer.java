package com.lc.netty.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取对应的pipeline 对象
//        ChannelPipeline channelPipeline = ch.pipeline();
//        // 往pipeline 中添加 handler对象
//        // LengthFieldBasedFrameDecoder 解码器
//        channelPipeline.addLast("lengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
//        // 编码器
//        channelPipeline.addLast("lengthFieldPrepender",new LengthFieldPrepender(4));
//        // 字符串的解码器
//        channelPipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
//        // 字符串编码器
//        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));
//        // 最后可以添加自己的处理器
//        channelPipeline.addLast(new MyClientHandler());
    }


}
