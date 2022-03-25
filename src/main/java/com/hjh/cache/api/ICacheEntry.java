package com.hjh.cache.api;

/**
 * 缓存实体
 * @author hongjinhui
 * 2022/3/24
 */

public interface ICacheEntry<K, V> {
    K key();
    V value();
}
