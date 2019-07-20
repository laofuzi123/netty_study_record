package com.lc.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws Exception {
        // builder 构造对象
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();
        // 转换成字节数组
        byte[] student2ByteArray = student.toByteArray();
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student2.toString());
        System.out.println("===============");
        System.out.println(student2.getAddress());
        System.out.println(student2.getAge());
        System.out.println(student2.getName());
    }
}
