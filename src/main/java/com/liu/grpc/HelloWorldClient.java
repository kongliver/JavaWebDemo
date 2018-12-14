package com.liu.grpc;

import com.liu.grpc.helloworld.GreeterGrpc;
import com.liu.grpc.helloworld.HelloReply;
import com.liu.grpc.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyi
 * grpc客户端
 */
public class HelloWorldClient {

    // 一个grpc信道
    private final ManagedChannel channel;
    // 阻塞/同步 存根
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port)
                // 定义需要证书
                .usePlaintext(true));
    }

    public HelloWorldClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
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
     * @param name 传入客户端需要的参数
     */
    public void greet(String name){
        // 实例化服务中方法需要的参数
        HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply;
        try {
            reply = blockingStub.sayHello(helloRequest);
        } catch (StatusRuntimeException e){
            System.out.println("rpc调用失败：" + e.getMessage());
            return;
        }
        System.out.println("服务器返回信息：" + reply.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("127.0.0.1", 8880);
        try {
            for (int i = 0; i < 5; i++) {
                client.greet("world" + i);
            }
        } finally {
            client.shutdown();
        }
    }
}
