package com.liu.pool;

import com.liu.grpc.HelloWorldClientSingle;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 客户端工厂：产生连接池所需要的客户端
 * @author liuyi
 * @date 2018/12/13
 */
public class HelloWorldFactory extends BasePooledObjectFactory<HelloWorldClientSingle> {

    private static String host;
    private static int port;

    static {
        // 可以从配置文件中读取，此处未实现
        host = "127.0.0.1";
        port = 8880;
    }

    @Override
    public HelloWorldClientSingle create() {
        return new HelloWorldClientSingle(HelloWorldFactory.host, HelloWorldFactory.port);
    }

    @Override
    public PooledObject<HelloWorldClientSingle> wrap(HelloWorldClientSingle helloWorldClientSingle) {
        return new DefaultPooledObject<HelloWorldClientSingle>(helloWorldClientSingle);
    }
}
