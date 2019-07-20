package com.lc.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

// NIO 异步编程
public class NIOTest12 {

    public static void main(String[] args) throws Exception {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        // 构造一个selector
        Selector selector = Selector.open();
        // 将 Selector 注册到对应的监听端口上
        for(int i=0;i<ports.length;++i){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 配置是否阻塞 true 阻塞 false 不阻塞
            serverSocketChannel.configureBlocking(false);
            // 构造sockte
            ServerSocket serverSocket = serverSocketChannel.socket();
            // 绑定
            InetSocketAddress socketAddress = new InetSocketAddress(ports[i]);
            serverSocket.bind(socketAddress);
            // 注册 通道和选择器之间的关联关系  将当前的selector注册到通道上 并且对应的感兴趣的key是 接受连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口:"+ports[i]);
        }

        while (true) {
            // 表示返回的键的数量
            int numbers = selector.select();
            System.out.println("numbers:"+numbers);
            // 一旦有返回 获取相应的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys:"+selectionKeys);
            // 获取他的迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if( selectionKey.isAcceptable() ) {
                    // 获取对应的 真正所关联的channel对象
                    ServerSocketChannel    serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    // 获取对应的 SockteChannel 这时表示的是真正的连接对象通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 连接建立后之后 需要将新的连接注册到 selector当中 感兴趣的事件是 读
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    // 以上调用完之后 需要调用迭代器的 remove  将其从 selectionKeys 集合中移除（这里特别重要，不然一直还是会监听）
                    iterator.remove();
                    System.out.println("获取到客户端的连接:"+socketChannel);
                } else  if (selectionKey.isReadable()) {
                    // 进行数据的读取
                    // 通过 selectionKey 获取对应的 channel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int byteRead = 0;
                    while (true) {
                        // 读取数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        int read = socketChannel.read(byteBuffer);
                        if ( read <=0 ) {
                            break;
                        }
                        // 往会写
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteRead += read;
                    }
                    System.out.println("读取的数据:"+byteRead+" 来自于:"+socketChannel);
                    // 特别注意 这里处理完 一定要把当前的 事件rremove
                    iterator.remove();

                }

            }
        }

    }

}
