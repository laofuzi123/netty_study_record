package com.lc.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NIOTest1 {

    public static void main(String[] args) {
        // 分配一个大小为10的缓冲区 里面只能放置整数
        IntBuffer intBuffer = IntBuffer.allocate(10);
        // 生成随机数
        for(int i=0;i<intBuffer.capacity();++i){
            // SecureRandom 生成的随机数 更加具有随机性
            int randomNumer = new SecureRandom().nextInt(20);
            intBuffer.put(randomNumer);
        }
        // 翻转 这里的作用是 在放入数据之后 如果需要进行读 则需要进行翻转 读写切换
        intBuffer.flip();
        // intBuffer 还有没有元素
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
