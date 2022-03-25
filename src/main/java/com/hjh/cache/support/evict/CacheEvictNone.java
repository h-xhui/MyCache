package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvictContext;

/**
 * 无淘汰策略
 *
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheEvictNone<K, V> extends AbstractCacheEvict<K, V>{
    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        return null;
    }
}
