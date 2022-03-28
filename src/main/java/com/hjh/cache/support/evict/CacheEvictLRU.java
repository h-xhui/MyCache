package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.model.CacheEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * 淘汰策略-LRU
 *
 * @author hongjinhui
 * 2022/3/25
 */

public class CacheEvictLRU<K, V> extends AbstractCacheEvict<K, V> {
    private final List<K> list = new LinkedList<>();

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            K evictKey = list.remove(0);
            V evictValue = cache.remove(evictKey);
            return new CacheEntry<>(evictKey, evictValue);
        }
        list.add(context.key());
        return null;
    }

    @Override
    public void updateKey(K key) {
        list.remove(key);
        list.add(key);
    }

    @Override
    public void removeKey(K key) {
        list.remove(key);
    }
}
