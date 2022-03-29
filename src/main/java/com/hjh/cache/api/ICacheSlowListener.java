package com.hjh.cache.api;

/**
 * 慢操作监听器
 *
 * @author hongjinhui
 * 2022/3/29
 */

public interface ICacheSlowListener<K, V> {
    void listen(ICacheSlowListenerContext<K, V> context);
    long showerThanMills();
}
