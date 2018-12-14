package com.liu.grpc;

import com.liu.grpc.helloworld.GreeterGrpc;
import com.liu.grpc.helloworld.HelloReply;
import com.liu.grpc.helloworld.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @author liuyi
 * grpc服务端
 */
public class HelloWorldServer {
    // 端口号
    private int port = 8880;
    private Server server;

    /**
     * 启动服务
     */
    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        System.out.println("service start...端口号为：" + port);

        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
            }
        });
    }

    /**
     * 关闭服务
     */
    private void stop() {
        if (server != null){
            server.shutdown();
        }
    }

    /**
     * 阻塞一直到退出程序
     */
    private void blockUtilShutdown() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUtilShutdown();
    }

    /**
     * 定义一个实现服务接口的类
     */
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase{

        @Override
        public void  sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            // 获取参数
            System.out.println("收到的消息" + request.getName());

            // 这里可以放置具体业务处理代码

            // 构造返回
            HelloReply reply = HelloReply.newBuilder().setMessage(("Hello：" + request.getName())).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
