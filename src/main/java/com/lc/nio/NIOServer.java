package com.lc.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class NIOServer {
    // 维护所有客户端的连接信息
    private static Map<String,SocketChannel> clientMap = new HashMap();

    public static void main(String[] args)  throws Exception{
        // 创建一个serverSockteChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 配置成非阻塞的
        serverSocketChannel.configureBlocking(false);
        // 获取serverSocket对象
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 绑定端口
        serverSocket.bind(new InetSocketAddress(8899));

        // 创建selector对象
        Selector selector = Selector.open();
        // 将serverSocketChannel对象注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 注册完成之后 进行相应的事件处理
        // 进行服务器的监听
        while (true) {
            try {
                // 这个方法将阻塞 一直到他所监听的 感兴趣的事件发生 返回他所关注的事件数量
                // 当这个方法返回之后 就可以获取他的selectionKey 所构成的集合
                selector.select();

                // 获取返回的集合对象
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历SelectionKey集合 取出对应的每种SelectionKey 判断对应的是什么事件 并进行相应的处理
                selectionKeys.forEach(selectionKey -> {
                    try {
                        // 表示对应客户端的 channel对象
                        final SocketChannel client;
                        if (selectionKey.isAcceptable()) {
                            // 可以通过 selectionKey 来获取与之关联的 serverSocketChannel对象
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            // 服务端 真正接收了客户端的连接会返回一个 SocketChannel对象
                            client = server.accept();
                            // client 连接真正建立之后 将客户端的channel对象 配置成非阻塞的并 注册到selector上
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);
                            // 将客户端的连接信息 记录到服务端 这样才可以实现服务端实现消息的分发
                            String key = "["+ UUID.randomUUID().toString()+"]";
                            clientMap.put(key,client);
                        }else if (selectionKey.isReadable()) {
                            // 判断是否有新进来的数据
                            // 获取 socketChannel
                            client = (SocketChannel) selectionKey.channel();
                            // 定义byteBuffer对象
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            // 将数据读入 buffer
                            int count = client.read(readBuffer);
                            if(count > 0){
                                // 进行写操作
                                readBuffer.flip();
                                // 进行字符集编码
                                Charset charset = Charset.forName("utf-8");
                                // 将buffer对象进行解码成字符串
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":"+receiveMessage);
                                // 获取到 sendKey
                                String sendKey = null;
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                                    if ( client == entry.getValue()) {
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }
                                // 进行分发
                                for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                                    // 获取每一个与服务端连接的 sockteChannel对象
                                    SocketChannel socketChannel = entry.getValue();
                                    // 进行数据的写入
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                    // 将需要发送的数据 写入
                                    writeBuffer.put((sendKey+":"+receiveMessage).getBytes());
                                    writeBuffer.flip();
                                    socketChannel.write(writeBuffer);
                                }

                            }
                        }
                    } catch (Exception e ) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
