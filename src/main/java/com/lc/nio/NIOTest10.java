package com.lc.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class NIOTest10 {

    // 文件锁的概念
    public static void main(String[] args) throws  Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NIOTest9.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock(2, 4, true);
        System.out.println(lock.isValid());
        System.out.println(lock.isShared());
        lock.release();
        randomAccessFile.close();
    }
}
