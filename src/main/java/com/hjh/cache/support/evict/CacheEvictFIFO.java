package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.model.CacheEntry;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 淘汰策略-先进先出
 *
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheEvictFIFO<K, V> extends AbstractCacheEvict<K, V> {
    private final Queue<K> queue = new LinkedList<>();
    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICacheEntry<K, V> result = null;
        final ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            K evictKey = queue.remove();
            V evictValue = cache.remove(evictKey);
            result = new CacheEntry<>(evictKey, evictValue);
        }
        queue.add(context.key());
        return result;
    }
}
