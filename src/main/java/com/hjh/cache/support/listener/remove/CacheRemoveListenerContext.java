package com.hjh.cache.support.listener.remove;

import com.hjh.cache.api.ICacheRemoveListenerContext;

/**
 * 删除监听器上下文
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheRemoveListenerContext<K, V> implements ICacheRemoveListenerContext<K, V> {
    private K key;
    private V value;
    private String type;

    public static <K, V> CacheRemoveListenerContext<K, V> getInstance() {
        return new CacheRemoveListenerContext<>();
    }

    @Override
    public K key() {
        return this.key;
    }

    public CacheRemoveListenerContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public V value() {
        return this.value;
    }

    public CacheRemoveListenerContext<K, V> value(V value) {
        this.value = value;
        return this;
    }

    @Override
    public String type() {
        return this.type;
    }

    public CacheRemoveListenerContext<K, V> type(String  type) {
        this.type = type;
        return this;
    }
}
