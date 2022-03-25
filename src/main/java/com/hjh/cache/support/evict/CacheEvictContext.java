package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEvictContext;

/**
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheEvictContext<K, V> implements ICacheEvictContext<K, V> {

    /**
     * 新添加的key
     */
    private K key;

    /**
     * cache实现
     */
    private ICache<K, V> cache;

    /**
     * 最大的大小
     */
    private int size;

    @Override
    public K key() {
        return key;
    }

    public CacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public ICache<K, V> cache() {
        return cache;
    }

    public CacheEvictContext<K, V> cache(ICache<K, V> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public int size() {
        return size;
    }

    public CacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }
}
