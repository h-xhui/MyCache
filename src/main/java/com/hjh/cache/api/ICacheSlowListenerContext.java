package com.hjh.cache.api;

/**
 * 慢操作监听器上下文
 *
 * @author hongjinhui
 * 2022/3/29
 */

public interface ICacheSlowListenerContext<K, V> {
    String methodName();
    Object[] params();
    Object result();
    long startMills();
    long endMills();
    long costMills();
}
