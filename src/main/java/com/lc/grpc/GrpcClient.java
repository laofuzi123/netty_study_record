package com.lc.grpc;

import com.lc.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8899)
                .usePlaintext(true) //普通文本的连接方式
                .build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        StudentServiceGrpc.StudentServiceStub studentServiceFutureStub = StudentServiceGrpc.newStub(channel);


        MyResponse my = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("lisi").build());
        System.out.println(my.getRealname());

        System.out.println("----------------------------");
        /*
        Iterator<StudentResponse> iter = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(10).build());
        while (iter.hasNext()){
            StudentResponse next = iter.next();
            System.out.println(next.getAge()+":"+next.getCity()+":"+next.getName());
        }
        System.out.println("-----------------------------");
        System.out.println("客户端发送流到服务端");


        StreamObserver<StudentResponseList> streamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                System.out.println("接收到服务端的请求");
                List<StudentResponse> studentResponseList = value.getStudentResponseList();
                for (StudentResponse studentResponse : studentResponseList) {
                    System.out.println(studentResponse.getAge()+":"+studentResponse.getCity()+":"+studentResponse.getName());
                }
            }
            @Override
            public void onError(Throwable t) {
                System.out.printf(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("------");
            }
        };
        StreamObserver<StudentRequest> studentWrapperByAges = studentServiceFutureStub.getStudentWrapperByAges(streamObserver);
        studentWrapperByAges.onNext(StudentRequest.newBuilder().setAge(10).build());
        studentWrapperByAges.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentWrapperByAges.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentWrapperByAges.onCompleted();
        */
        /*
        StreamObserver<StreamResponse> streamResponseStreamObserver = new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println("接收到请求:");
                System.out.println(value.getResponseInfo());
                System.out.println("**************");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("---------end---------");
            }
        };
        StreamObserver<StreamRequest> streamRequestStreamObserver = studentServiceFutureStub.biTalk(streamResponseStreamObserver);
        for(int i=0;i<10;i++) {
            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        streamRequestStreamObserver.onCompleted();

        try {
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
