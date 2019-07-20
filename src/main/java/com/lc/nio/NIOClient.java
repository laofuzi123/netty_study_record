package com.lc.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClient {

    public static void main(String[] args) throws  Exception{

        try {
            // 建立连接
            SocketChannel socketChannel = SocketChannel.open();
            // 配置非阻塞模式
            socketChannel.configureBlocking(false);

            // 定义 selector 并注册
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            // 连接
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) {
                        // 表示 连接上了 获取 socketChannel对象
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        // 连接是否处在是否进行的状态
                        if ( client.isConnectionPending()) {
                            // 完成连接
                            client.finishConnect();
                            // 表示连接真正的建立好了
                            ByteBuffer wirteBuffer = ByteBuffer.allocate(512);
                            wirteBuffer.put((LocalDateTime.now()+"连接成功").getBytes());
                            wirteBuffer.flip();
                            client.write(wirteBuffer);
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(()->{
                                while (true) {
                                    try {
                                        // 键盘输入
                                        wirteBuffer.clear();
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String sendMessage = bufferedReader.readLine();
                                        wirteBuffer.put(sendMessage.getBytes());
                                        wirteBuffer.flip();
                                        client.write(wirteBuffer);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        // 注册读取事件
                        client.register(selector,SelectionKey.OP_READ);
                    }else  if( selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        // 读取服务器端发送过来的数据
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int read = client.read(readBuffer);
                        if( read > 0) {
                            String receivedMessage = new String(readBuffer.array(),0,read);
                            System.out.println(receivedMessage);
                        }
                    }
                }
                selectionKeys.clear();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
