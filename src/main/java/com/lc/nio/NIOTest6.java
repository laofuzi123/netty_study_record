package com.lc.nio;

import java.nio.ByteBuffer;

public class NIOTest6 {

    // 只读buffer
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass());
        for(int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte)i);
        }

        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        System.out.println(byteBuffer1.getClass());


        byteBuffer1.position(2);
        byteBuffer1.limit(5);
        ByteBuffer slice = byteBuffer1.slice();
        slice.put(3,(byte)44);
        System.out.println(slice.get(3));
    }
}
