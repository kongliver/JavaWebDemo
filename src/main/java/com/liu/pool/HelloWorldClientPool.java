package com.liu.pool;

import com.liu.grpc.HelloWorldClientSingle;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 客户端连接池
 * @author liuyi
 * @date 2018/12/13
 */
public class HelloWorldClientPool {
    private static GenericObjectPool<HelloWorldClientSingle> objectPool;

    static {
        // 连接池配置，可以读取配置文件，此处未实现
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
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
        // 连接池创建
        objectPool = new GenericObjectPool<HelloWorldClientSingle>(new HelloWorldFactory(), poolConfig);
    }

    /**
     * 借出一个连接
     * @return 客户端连接
     */
    private static HelloWorldClientSingle borrowObject(){
        try {
            HelloWorldClientSingle clientSingle = objectPool.borrowObject();
            System.out.println("总创建线程数：" + objectPool.getCreatedCount());
            return clientSingle;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果连接池失败则手动创建
        return createClient();
    }

    /**
     * 连接池异常时手动创建对象
     * @return 一个客户端
     */
    private static HelloWorldClientSingle createClient() {
        return new HelloWorldClientSingle("127.0.0.1", 8880);
    }

    /**
     * 收回连接
     * @param clientSingle 一个客户端
     */
    private static void returnObject(HelloWorldClientSingle clientSingle){
        // 将连接对象返回给连接池
        objectPool.returnObject(clientSingle);
    }

    /**
     * 执行器
     * @param workCallBack 主要服务内容
     * @return 线程
     */
    public static Runnable execute(final WorkCallBack<HelloWorldClientSingle> workCallBack){
        return new Runnable() {

            @Override
            public void run() {
                HelloWorldClientSingle clientSingle = HelloWorldClientPool.borrowObject();
                try {
                    workCallBack.callback(clientSingle);
                } finally {
                    // 将连接对象返回给连接池
                    HelloWorldClientPool.returnObject(clientSingle);
                }
            }
        };
    }
}
