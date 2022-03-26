package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheEvictContext;

/**
 * 淘汰策略抽象类
 *
 * @author hongjinhui
 * 2022/3/24
 */

public abstract class AbstractCacheEvict<K, V> implements ICacheEvict<K, V> {
    @Override
    public ICacheEntry<K, V> evict(ICacheEvictContext<K, V> context) {
        return doEvict(context);
    }

    /**
     * 执行淘汰
     * @param context 上下文
     * @return 移除的结果
     */
    protected abstract ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context);

    @Override
    public void updateKey(K key) {
    }

    @Override
    public void removeKey(K key) {

    }
}
