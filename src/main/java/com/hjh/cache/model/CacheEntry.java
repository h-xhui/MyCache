package com.hjh.cache.model;

import com.hjh.cache.api.ICacheEntry;

/**
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheEntry<K, V> implements ICacheEntry<K, V> {

    private final K key;
    private final V value;

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
