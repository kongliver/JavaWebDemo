package com.liu.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * 
 * @function   信道工厂
 * @author     极客空
 * @date       2018年12月16日 下午5:04:15
 * @copyright  MR.Liu
 * @address    成都
 *
 */
public class ChannelFactory extends BasePooledObjectFactory<ManagedChannel> {

    private static String host;
    private static int port;

    @SuppressWarnings("deprecation")
    @Override
    public ManagedChannel create() throws Exception {
        return ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    }
    
    @Override
    public PooledObject<ManagedChannel> wrap(ManagedChannel channel) {
        return new DefaultPooledObject<ManagedChannel>(channel);
    }
    
    public static String getHost() {
        return host;
    }
    
    public static void setHost(String host) {
        ChannelFactory.host = host;
    }
    
    public static int getPort() {
        return port;
    }
    
    public static void setPort(int port) {
        ChannelFactory.port = port;
    }
    
    
}
