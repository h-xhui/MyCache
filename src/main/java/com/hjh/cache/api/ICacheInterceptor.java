package com.hjh.cache.api;

/**
 * @author hongjinhui
 * 2022/3/25
 */

public interface ICacheInterceptor<K, V> {

    /**
     * 方法执行前
     * @param context
     */
    void before(ICacheInterceptorContext<K, V> context);

    /**
     * 方法执行后
     * @param context
     */
    void after(ICacheInterceptorContext<K, V> context);
}
