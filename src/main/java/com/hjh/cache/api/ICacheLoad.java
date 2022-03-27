package com.hjh.cache.api;

/**
 * @author hongjinhui
 * 2022/3/26
 */

public interface ICacheLoad<K, V> {

    void load(ICache<K, V> cache);
}
