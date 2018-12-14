package com.liu.grpc;

import com.liu.pool.HelloWorldClientPool;
import com.liu.pool.WorkCallBack;

/**
 * 测试连接池
 * @author liuyi
 * @date 2018/12/13
 */
public class TestPool {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            final int count = i;
            new Thread(HelloWorldClientPool.execute(new WorkCallBack<HelloWorldClientSingle>() {
                @Override
                public void callback(HelloWorldClientSingle clientSingle) {
                    clientSingle.greet("world" + (count + 1));
                }
            })).start();
        }
    }
}
