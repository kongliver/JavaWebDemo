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
    private static ChannelPool channelPool;
    
    static {
        host = "127.0.0.1";
        port = 8880;
        ChannelFactory channelFactory = new ChannelFactory(host, port);
        HelloWorldPoolConfig poolConfig = new HelloWorldPoolConfig();
        channelPool = new ChannelPool(poolConfig, channelFactory);
    }
    
    public static GreeterBlockingStub getStub() {
        ManagedChannel channel = null;
        try {
            channel = channelPool.borrowChannel();
            return GreeterGrpc.newBlockingStub(channel);
        } finally {
            channelPool.returnChannel(channel);
        } 
    }
}
