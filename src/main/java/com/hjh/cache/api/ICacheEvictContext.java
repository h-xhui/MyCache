package com.hjh.cache.api;

/**
 * @author 洪锦辉
 * 2022/3/24
 */

public interface ICacheEvictContext<K, V> {
    /**
     * 新加的key
     * @return
     */
    K key();

    /**
     * cache的实现
     * @return
     */
    ICache<K, V> cache();

    /**
     * 获取大小
     * @return
     */
    int size();
}
