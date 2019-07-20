package com.lc.grpc;

import com.lc.proto.*;
import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {

        System.out.println("接收到客户端的请求:"+request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端的请求："+request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(10).setCity("北京").setName("张三").build());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(StudentResponse.newBuilder().setAge(10).setCity("南京").setName("李四").build());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(StudentResponse.newBuilder().setAge(10).setCity("上海").setName("王五").build());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(StudentResponse.newBuilder().setAge(10).setCity("深圳").setName("杨柳").build());
        responseObserver.onCompleted();
    }

    // 客户端发送一个流 服务端返回一个响应
    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        System.out.println("接收到客户端的流式请求:----------");
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("接收到客户端的请求:"+value.getAge());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                StudentResponseList build = StudentResponseList.newBuilder().build();
                StudentResponseList build1 = StudentResponseList.newBuilder()
                        .addStudentResponse(StudentResponse.newBuilder().setName("张三").setCity("南京").setAge(10).build())
                        .addStudentResponse(StudentResponse.newBuilder().setName("李四").setCity("北京").setAge(10).build())
                        .addStudentResponse(StudentResponse.newBuilder().setName("王五").setCity("上海").setAge(10).build())
                        .build();
                responseObserver.onNext(build1);
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println("onNext:"+value.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo("你好:"+value.getRequestInfo()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

    }
}
