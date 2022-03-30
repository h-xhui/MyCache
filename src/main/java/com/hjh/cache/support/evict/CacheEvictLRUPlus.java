package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.model.CacheEntry;
import com.hjh.cache.support.evict.struct.LruLinkedHashMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 优化LRU
 *
 * @author hongjinhui
 * 2022/3/30
 */

public class CacheEvictLRUPlus<K, V> extends AbstractCacheEvict<K, V> {
    private final LruLinkedHashMap<K, V> lruLinkedHashMap  = new LruLinkedHashMap<>();

    @Override
    public ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            lruLinkedHashMap.setRemoveFlag(true);
            lruLinkedHashMap.put(context.key(), null);
            K evictKey = lruLinkedHashMap.getOldestEntry().getKey();
            V evictValue = cache.remove(evictKey);
            return new CacheEntry<>(evictKey, evictValue);
        } else {
            lruLinkedHashMap.setRemoveFlag(false);
        }

        return null;
    }

    @Override
    public void updateKey(K key) {
        lruLinkedHashMap.put(key, null);
    }

    @Override
    public void removeKey(K key) {
        lruLinkedHashMap.remove(key);
    }
}
