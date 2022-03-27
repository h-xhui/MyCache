package com.hjh.cache.support.persist;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICachePersist;

import java.util.concurrent.TimeUnit;

/**
 * 持久化实现-缺省适配器模式
 *
 * @author hongjinhui
 * 2022/3/27
 */

public class CachePersistAdaptor<K, V> implements ICachePersist<K, V> {
    @Override
    public void persist(ICache<K, V> cache) {

    }

    @Override
    public long delay() {
        return 1;
    }

    @Override
    public long period() {
        return 1;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }
}
