package com.lc.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest4 {

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("niotest3.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        // 将数据写入buffer
        byte[] message = "Hello,message!".getBytes();
        for(int i=0;i<message.length;i++){
            byteBuffer.put(message[i]);
        }
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
