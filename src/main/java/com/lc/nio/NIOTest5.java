package com.lc.nio;

import java.nio.ByteBuffer;

public class NIOTest5 {


    // 分割或者分片buffer
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte) i);
        }
        byteBuffer.position(2);
        byteBuffer.limit(6);
        ByteBuffer slice = byteBuffer.slice();
        for(int i=0;i<slice.capacity();i++){
            byte b = slice.get(i);
            b *=2;
            slice.put(i,b);
        }
        byteBuffer.clear();
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }
}
