package com.lc.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest2 {

    // 传统io 如何切换到nio
    public static void main(String[] args)  throws  Exception{
        FileInputStream fileInputStream = new FileInputStream("niotest.txt");
        FileChannel channel = fileInputStream.getChannel();
        // 构造ByteBuffer对象并分配大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        // 将文件对象读入到 byteBuffer中
        channel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.remaining() >0) {
            byte b = byteBuffer.get();
            System.out.println("char:"+(char)b);
        }

        fileInputStream.close();
    }
}
