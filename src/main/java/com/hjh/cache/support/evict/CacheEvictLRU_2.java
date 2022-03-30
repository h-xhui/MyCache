package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.model.CacheEntry;
import com.hjh.cache.support.evict.struct.LruLinkedHashMap;

/**
 * LRU-K算法
 *
 * @author hongjinhui
 * 2022/3/30
 */

public class CacheEvictLRU_2<K, V> extends AbstractCacheEvict<K, V> {
    private final LruLinkedHashMap<K, V> first = new LruLinkedHashMap<>();
    private final LruLinkedHashMap<K, V> second = new LruLinkedHashMap<>();

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            if (first.size() != 0) {
                first.setRemoveFlag(true);
                first.put(context.key(), null);
                K evictKey = first.getOldestEntry().getKey();
                V evictValue = cache.remove(evictKey);
                return new CacheEntry<>(evictKey, evictValue);
            } else {
                second.setRemoveFlag(true);
                second.put(context.key(), null);
                K evictKey = second.getOldestEntry().getKey();
                V evictValue = cache.remove(evictKey);
                return new CacheEntry<>(evictKey, evictValue);
            }
        }

        return null;
    }

    @Override
    public void updateKey(K key) {
        if (first.containsKey(key) || second.containsKey(key)) {
            first.remove(key);
            second.put(key, null);
        } else {
            first.put(key, null);
        }
    }

    @Override
    public void removeKey(K key) {
        if (second.containsKey(key)) {
            second.remove(key);
        } else {
            first.remove(key);
        }
    }
}
