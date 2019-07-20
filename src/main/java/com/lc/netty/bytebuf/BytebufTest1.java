package com.lc.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class BytebufTest1 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello你 world", Charset.forName("utf-8"));

        if( byteBuf.hasArray()){
            // hasArray 判断这个byteBuf背后真正的支持是不是一个字节数组 如果是 表示这是一个堆上的缓冲
            // 获取byteBuf背后的真正数据载体
            byte[] array = byteBuf.array();
            System.out.println(new String(array,Charset.forName("utf-8")));
            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());

            while (byteBuf.isReadable()){
                System.out.println((char) byteBuf.readByte());
            }
        }
    }
}
