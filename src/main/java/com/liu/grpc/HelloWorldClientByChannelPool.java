package com.liu.grpc;

import com.liu.grpc.helloworld.HelloReply;
import com.liu.grpc.helloworld.HelloRequest;
import com.liu.pool.HelloWorldGrpcConfig;

import io.grpc.StatusRuntimeException;

/**
 * @author liuyi
 * grpc客户端
 */
public class HelloWorldClientByChannelPool {

    /**
     * 客户端方法
     * @param name 传入客户端需要的参数
     */
    public void greet(String name){
        // 实例化服务中方法需要的参数
        HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply;
        try {
            reply = HelloWorldGrpcConfig.getStub().sayHello(helloRequest);
        } catch (StatusRuntimeException e){
            System.out.println("rpc调用失败：" + e.getMessage());
            return;
        }
        System.out.println("服务器返回信息：" + reply.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClientByChannelPool client = new HelloWorldClientByChannelPool();
        for (int i = 0; i < 20; i++) {
            client.greet("world" + (i + 1));
        }
    }
}
