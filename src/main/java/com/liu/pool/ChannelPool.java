package com.liu.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * 
 * @function   信道连接池
 * @author     极客空
 * @date       2018年12月16日 下午5:11:16
 * @copyright  MR.Liu
 * @address    成都
 *
 */
public class ChannelPool {

    private static GenericObjectPool<ManagedChannel> channelPool;
    
    private GenericObjectPoolConfig<ManagedChannel> poolConfig;
    
    private ChannelFactory channelFactory;

    public ChannelPool(GenericObjectPoolConfig<ManagedChannel> poolConfig, ChannelFactory channelFactory) {
        this.poolConfig = poolConfig;
        this.channelFactory = channelFactory;
        channelPool = new GenericObjectPool<ManagedChannel>(channelFactory, poolConfig);
    }

    public ManagedChannel borrowChannel() {
        try {
            System.out.println("总创建线程数：" + channelPool.getCreatedCount());
            return channelPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createChannel();
    }

    @SuppressWarnings("deprecation")
    private ManagedChannel createChannel() {
        return ManagedChannelBuilder.forAddress(channelFactory.getHost(), channelFactory.getPort())
                .usePlaintext(true).build();
    }
    
    public void returnChannel(ManagedChannel channel) {
        channelPool.returnObject(channel);
    }
    
    public GenericObjectPoolConfig<ManagedChannel> getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(GenericObjectPoolConfig<ManagedChannel> poolConfig) {
        this.poolConfig = poolConfig;
    }

    public ChannelFactory getChannelFactory() {
        return channelFactory;
    }

    public void setChannelFactory(ChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
    }
}
