package com.lc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NIOTest11 {

    // 关于buffer的Scattering(分散) 和Gathering(合并)
    public static void main(String[] args) throws  Exception {
        // 构造一个 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定一个端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 构造三个buffer来进行数据的读取
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel accept = serverSocketChannel.accept();

        while (true){
            // 等待读
            long read = 0;
            while (read < messageLength) {
                long read1 = accept.read(byteBuffers);
                if(-1 != read1) {
                    read += read1;
                    System.out.println("read:" + read);
                    // 读完之后 打印对应position limit信息
                    Arrays.asList(byteBuffers).stream().map(buffer ->
                            "position:" + buffer.position() + "limit:" + buffer.limit()
                    ).forEach(System.out::println);
                }
            }

            // 读完准备写
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            long write = 0;
            while (write < messageLength) {
                long write1 = accept.write(byteBuffers);
                write += write1;
            }

            // 写完之后需要clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("read:"+read+" write:"+write+" message:"+messageLength);



        }

    }
}
