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
    
    static {
        // 连接池配置，可以读取配置文件，此处未实现
        GenericObjectPoolConfig<ManagedChannel> poolConfig = new GenericObjectPoolConfig<ManagedChannel>();
        // 池中的最大连接数
        poolConfig.setMaxTotal(8);
        // 最少的空闲连接数
        poolConfig.setMinIdle(0);
        // 最多的空闲连接数
        poolConfig.setMaxIdle(8);
        // 当连接资源耗尽时，调用者最大阻塞的时间，超时后抛出异常 单位：毫秒数
        poolConfig.setMaxWaitMillis(-1);
        // 连接池存放池对象方式，true放在空闲队列最前面，false放在空闲队列最后面
        poolConfig.setLifo(true);
        // 连接空闲的最小时间，达到此值后空闲连接数可能会被移除，默认为30分钟
        poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
        // 连接耗尽时是否阻塞，默认为true
        poolConfig.setBlockWhenExhausted(true);
        
        channelPool = new GenericObjectPool<ManagedChannel>(new ChannelFactory(), poolConfig);
    }
    
    public static ManagedChannel borrowChannel() {
        try {
            System.out.println("总创建线程数：" + channelPool.getCreatedCount());
            return channelPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createChannel();
    }

    @SuppressWarnings("deprecation")
    private static ManagedChannel createChannel() {
        return ManagedChannelBuilder.forAddress(ChannelFactory.getHost(), ChannelFactory.getPort())
                .usePlaintext(true).build();
    }
    
    public static void returnChannel(ManagedChannel channel) {
        channelPool.returnObject(channel);
    }
}
