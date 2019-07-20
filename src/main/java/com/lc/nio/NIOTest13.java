package com.lc.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NIOTest13 {

    // java编解码
    public static void main(String[] args)  throws  Exception{

        // 定义一个输入文件
        String inputFile = "NIOTest13_in.txt";
        String outputFile = "NIOTest13_out.txt";

        // 将 NIOTest13_in.txt文件内容拷贝到 NIOTest13_out中 使用内存映射
        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");
        // 获取输入文件的长度
        long fileLong = new File(inputFile).length();
        // 获取输入和输出的文件channle
        FileChannel inputChannel = inputRandomAccessFile.getChannel();
        FileChannel outputChannel = outputRandomAccessFile.getChannel();
        // 通过内存映射文件 修改内存内容 直接反应在文件上
        MappedByteBuffer mappedByteBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY,0,fileLong);
        // 指定字符集
        Charset charset = Charset.forName("utf-8");
        // decoder 将字节数组转化成字符串
        CharsetDecoder decoder = charset.newDecoder();
        // encoder 将字符串转化成字节数组
        CharsetEncoder encoder = charset.newEncoder();
        // 将内存映射的buffer 解码成一个 charbuffer
        CharBuffer charBuffer = decoder.decode(mappedByteBuffer);
        // 将 charbuffer 编码成 bytebuffer
        ByteBuffer outputData = encoder.encode(charBuffer);
        // 将 outputData 输出到文件通道
        outputChannel.write(outputData);
        // 关闭
        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
