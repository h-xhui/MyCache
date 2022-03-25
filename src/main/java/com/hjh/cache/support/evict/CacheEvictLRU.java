package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvictContext;

/**
 * 淘汰策略-LRU
 *
 * @author hongjinhui
 * 2022/3/25
 */

public class CacheEvictLRU<K, V> extends AbstractCacheEvict<K, V> {
    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        return null;
    }
}
