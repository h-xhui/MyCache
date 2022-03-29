package com.hjh.cache.api;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public interface ICacheRemoveListenerContext<K, V> {
    K key();
    V value();

    /**
     * 删除类型
     * @return 删除类型
     */
    String type();
}
