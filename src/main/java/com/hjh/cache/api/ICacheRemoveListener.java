package com.hjh.cache.api;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public interface ICacheRemoveListener<K, V> {

    /**
     * 监听
     * @param context 上下文
     */
    void listen(ICacheRemoveListenerContext<K, V> context);
}
