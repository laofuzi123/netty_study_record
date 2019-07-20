package com.lc.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        // 连接客户端
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        // 配置阻塞模式
        socketChannel.configureBlocking(true);
        String filename = "/Users/mac/Desktop/04 Vue绑定属性 绑定Html  绑定class  绑定style.zip";
        // 获取与文件锁关联的 fileChannel通道
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        long totalCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数:"+totalCount+",耗时"+(System.currentTimeMillis()-startTime));
        fileChannel.close();

    }
}
