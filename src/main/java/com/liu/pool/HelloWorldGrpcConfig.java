package com.liu.pool;

import com.liu.grpc.helloworld.GreeterGrpc;
import com.liu.grpc.helloworld.GreeterGrpc.GreeterBlockingStub;

import io.grpc.ManagedChannel;

/**
 * 
 * @function   配置得到存根
 * @author     极客空
 * @date       2018年12月16日 下午5:23:50
 * @copyright  MR.Liu
 * @address    成都
 *
 */
public class HelloWorldGrpcConfig {

    private static String host;
    private static int port;
    
    static {
        host = "127.0.0.1";
        port = 8880;
    }
    
    public static GreeterBlockingStub getStub() {
        ChannelFactory.setHost(HelloWorldGrpcConfig.host);
        ChannelFactory.setPort(HelloWorldGrpcConfig.port);
        ManagedChannel channel = null;
        try {
            channel = ChannelPool.borrowChannel();
            return GreeterGrpc.newBlockingStub(channel);
        } finally {
            ChannelPool.returnChannel(channel);
        } 
    }
}
