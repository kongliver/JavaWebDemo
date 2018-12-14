package com.liu.grpc;

import com.liu.grpc.helloworld.GreeterGrpc;
import com.liu.grpc.helloworld.HelloReply;
import com.liu.grpc.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * 创建单个客户端
 * @author liuyi
 * @date 2018/12/13
 */
public class HelloWorldClientSingle {
    /** grpc管道 */
    private final ManagedChannel channel;
    /** 阻塞/同步 存根 */
    private GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    /**
     * 初始化信道和存根
     * @param host ip地址
     * @param port 端口号
     */
    public HelloWorldClientSingle(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    }

    /**
     * 关闭客户端
     * @throws InterruptedException
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * 客户端方法
     * @param name 服务器需要的参数
     */
    public void greet(String name){
        greeterBlockingStub = GreeterGrpc.newBlockingStub(channel).withCompression("gzip");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply;
        try {
            reply = greeterBlockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            System.out.println("rpc调用失败：" + e.getMessage());
            return;
        }
        System.out.println("服务器返回消息：" + reply.getMessage());
    }
}
