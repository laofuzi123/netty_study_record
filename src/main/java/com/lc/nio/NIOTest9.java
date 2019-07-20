package com.lc.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest9 {

    // 内存映射文件  一个文件的内存映射区域
    public static void main(String[] args) throws  Exception {
        // rw 表示可读写
        RandomAccessFile randomAccessFile = new RandomAccessFile("NIOTest9.txt","rw");
        // 获取文件通道对象
        FileChannel fileChannel = randomAccessFile.getChannel();
        // 获取MappedBuffer对象
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        // 通过MappedByteBuffer 修改文件信息
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');
        randomAccessFile.close();
    }
}
