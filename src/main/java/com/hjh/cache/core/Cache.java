package com.hjh.cache.core;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.exception.CacheRuntimeException;
import com.hjh.cache.support.evict.CacheEvictContext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author hongjinhui
 * 2022/3/24
 */

public class Cache<K, V> implements ICache<K, V> {
    private Map<K, V> map;
    private int sizeLimit;
    private ICacheEvict<K, V> evict;

    /**
     * 设置map的实现
     * @param map
     * @return
     */
    public Cache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    /**
     * 设置大小限制
     * @param sizeLimit
     * @return
     */
    public Cache<K, V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    /**
     * 设置淘汰策略
     * @param cacheEvict
     * @return
     */
    public Cache<K, V> evict(ICacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }

    @Override
    public ICacheEvict<K, V> evict() {
        return this.evict;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        CacheEvictContext<K, V> context  = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        ICacheEntry<K, V> evictEntry = evict.evict(context);

        // 防止没有缓存策略时，超出限制
        if (this.size() >= sizeLimit) {
            throw new CacheRuntimeException("缓存已满，数据添加失败");
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
