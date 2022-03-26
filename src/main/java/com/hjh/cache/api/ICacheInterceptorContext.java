package com.hjh.cache.api;

import java.lang.reflect.Method;

/**
 * @author hongjinhui
 * 2022/3/25
 */

public interface ICacheInterceptorContext<K, V> {
    /**
     * 缓存消息
     * @return
     */
    ICache<K, V> cache();

    /**
     * 执行方法消息
     * @return
     */
    Method method();

    /**
     * 执行参数
     * @return
     */
    Object[] params();

    /**
     * 方法返回结果
     * @return
     */
    Object result();

    /**
     * 开始时间
     * @return
     */
    long startMills();

    /**
     * 结束时间
     * @return
     */
    long endMills();
}
