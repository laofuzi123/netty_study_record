package com.lc.netty.protobuf2netty;

import com.lc.protobuf.DataInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoderNano;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.CharsetUtil;

public class MyProtoBufInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(1048576,0,4,0,4));
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(DataInfo.Student.getDefaultInstance()));
        //编码器
        pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));
        pipeline.addLast("protobufEncoder",new ProtobufEncoder());
        pipeline.addLast(null);
    }
}
