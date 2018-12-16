package com.liu.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import io.grpc.ManagedChannel;

public class HelloWorldPoolConfig extends GenericObjectPoolConfig<ManagedChannel> {

    public HelloWorldPoolConfig() {
        // 池中的最大连接数
        super.setMaxTotal(8);
        // 最少的空闲连接数
        super.setMinIdle(0);
        // 最多的空闲连接数
        super.setMaxIdle(8);
        // 当连接资源耗尽时，调用者最大阻塞的时间，超时后抛出异常 单位：毫秒数
        super.setMaxWaitMillis(-1);
        // 连接池存放池对象方式，true放在空闲队列最前面，false放在空闲队列最后面
        super.setLifo(true);
        // 连接空闲的最小时间，达到此值后空闲连接数可能会被移除，默认为30分钟
        super.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
        // 连接耗尽时是否阻塞，默认为true
        super.setBlockWhenExhausted(true);
    }
}
