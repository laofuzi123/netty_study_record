package com.lc.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

public class BytebufTest2 {

    public static void main(String[] args) {
        // 创建一个 CompositeBuffer 复合缓冲区
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(8);
        // 将堆缓冲 和直接缓冲添加到复合缓冲区中
        compositeByteBuf.addComponents(heapBuf,directBuf);
        //compositeByteBuf.removeComponent(0);
        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        compositeByteBuf.forEach(System.out::println);
    }
}
