package com.lc.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyProtoBufClientHandler extends SimpleChannelInboundHandler<MyMessage.MyMessageInfo> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage.MyMessageInfo msg) throws Exception {

    }

    /**
     * channel 活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = new Random().nextInt(3);
        if(random == 0){
            MyMessage.MyMessageInfo cat = MyMessage.MyMessageInfo.newBuilder().setDateType(MyMessage.MyMessageInfo.DataType.Cat)
                    .setCat(
                            MyMessage.Cat.newBuilder().setAge(1).setName("小花").build()
                    ).build();
            ctx.writeAndFlush(cat);
        }else if(random == 1){
            MyMessage.MyMessageInfo dog = MyMessage.MyMessageInfo.newBuilder().setDateType(MyMessage.MyMessageInfo.DataType.Dog)
                    .setDog(
                            MyMessage.Dog.newBuilder().setAge(5).setName("小黑").build()
                    ).build();
            ctx.writeAndFlush(dog);
        }else{
            MyMessage.MyMessageInfo person = MyMessage.MyMessageInfo.newBuilder().setDateType(MyMessage.MyMessageInfo.DataType.Person)
                    .setPerson(
                            MyMessage.Person.newBuilder().setAge(50).setName("李四").setAddress("南极").build()
                    ).build();
            ctx.writeAndFlush(person);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
