package com.lc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest3 {

    // 输出流
    public static void main(String[] args) throws  Exception{

        try(
            FileOutputStream fileOutputStream = new FileOutputStream("niotest2.txt");
            FileInputStream fileInputStream = new FileInputStream("niotest.txt");
            ){
            FileChannel  inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            while (true){
                byteBuffer.clear();
                int read = inputChannel.read(byteBuffer);
                System.out.println("read:"+read);
                if(-1 == read){
                    break;
                }
                byteBuffer.flip();
                outputChannel.write(byteBuffer);
            }
        }
    }
}
