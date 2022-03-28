package com.hjh.cache.support.load;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheLoad;

/**
 * 加载类测试
 *
 * @author hongjinhui
 * 2022/3/26
 */

public class CacheLoadNone<K, V> implements ICacheLoad<K, V> {
    @Override
    public void load(ICache<K, V> cache) {

    }
}
