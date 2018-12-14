package com.liu.pool;

/**
 * 回调接口
 * @author liuyi
 * @date 2018/12/13
 */
public interface WorkCallBack<T> {

    /**
     * 回调函数
     * @param t 参数
     */
    void callback(T t);
}
