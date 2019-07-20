package com.lc.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {
    // 比较传统io 与 0拷贝之间的性能差异
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);
        // 不断等待客户端的连接
        while (true) {
            // 这是一个阻塞的方法
            Socket accept = serverSocket.accept();
            // 由于实例是一个文件 在java中文件是 二进制的数据
            DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
            try {
                // 定义缓冲区 字节数组
                byte[] byteArray = new byte[4096];
                // 源源不断的获取对端发送过来的数据
                while ( true ){
                    // 将 dataInputStream数据读入byteArray中 并返回实际读入的字节数
                    int read = dataInputStream.read(byteArray, 0, byteArray.length);
                    if( -1 == read ){
                        break;
                    }
                }


            } catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
