package com.lc.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BytebufTest0 {

    public static void main(String[] args) {
        // 创建一个长度为10 的Bytebuf
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i=0;i<10;i++) {
            byteBuf.writeByte(i);
        }

        for ( int i=0; i<byteBuf.capacity();i++){
            System.out.println(byteBuf.getByte(i));
        }
    }
}
