package com.lc.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OldClient {

    public static void main(String[] args) throws Exception {
        // 建立连接
        Socket socket = new Socket("localhost",8899);
        // 从文件读取信息
        String filename = "/Users/mac/Desktop/04 Vue绑定属性 绑定Html  绑定class  绑定style.zip";
        InputStream inputStream = new FileInputStream(filename);
        // 获取输出流
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[4096];
        long readCount;
        long totalCount = 0;

        long startTime = System.currentTimeMillis();
        while ( (readCount = inputStream.read(bytes)) >= 0 ) {
            totalCount += readCount;
            dataOutputStream.write(bytes);
        }
        System.out.println("发送总字节数:"+totalCount+",耗时"+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();

    }
}
