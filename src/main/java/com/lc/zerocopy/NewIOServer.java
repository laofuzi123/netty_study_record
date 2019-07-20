package com.lc.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {

    //采用0拷贝方式
    public static void main(String[] args)  throws  Exception{
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 获取与这个通道锁关联的socket对象
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 设置相应的属性
        serverSocket.setReuseAddress(true);
        serverSocket.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 配置成阻塞的模式
            socketChannel.configureBlocking(true);
            int readCount = 0;
            while ( -1 != readCount) {
                try {
                    // 将数据读入byteBuffer
                    readCount = socketChannel.read(byteBuffer);
                }catch (Exception e){
                    e.printStackTrace();
                }
                byteBuffer.rewind();//归位
//                byteBuffer.flip();
            }
        }

    }
}
