package com.hjh.cache.Bs;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheLoad;
import com.hjh.cache.api.ICachePersist;
import com.hjh.cache.core.Cache;
import com.hjh.cache.support.evict.CacheEvicts;
import com.hjh.cache.support.expire.CacheExpire;
import com.hjh.cache.support.load.CacheLoadNone;
import com.hjh.cache.support.persist.CachePersistDbJSON;
import com.hjh.cache.support.persist.InnerCachePersist;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存引导类
 *
 * @author hongjinhui
 * 2022/3/24
 */

public final class CacheBs<K, V> {
    private CacheBs() {}

    /**
     * 创建对象实例
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> CacheBs<K, V> newInstance() {
        return new CacheBs<>();
    }

    private Map<K, V> map = new HashMap<>();
    private int size = Integer.MAX_VALUE;
    private ICacheEvict<K, V> evict = CacheEvicts.FIFO();
    private ICacheLoad<K, V> load;
    private ICachePersist<K, V> persist;

    public CacheBs<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public CacheBs<K, V> size(int size) {
        this.size = size;
        return this;
    }

    public CacheBs<K, V> evict(ICacheEvict<K, V> evict) {
        this.evict = evict;
        return this;
    }

    public CacheBs<K, V> load(ICacheLoad<K, V> load) {
        this.load = load;
        return this;
    }

    public CacheBs<K, V> persist(ICachePersist<K, V> persist) {
        this.persist = persist;
        return this;
    }

    public Cache<K, V> build() {
        Cache<K, V> cache = new Cache<>();
        cache.map(map);
        cache.sizeLimit(size);
        cache.evict(evict);
        cache.load(load);
        cache.persist(persist);

        return cache;
    }

    public static void main(String[] args) {
        Cache<String, String> cache = CacheBs.<String, String>newInstance()
                .size(3).evict(CacheEvicts.FIFO()).load(new CacheLoadNone()).persist(new CachePersistDbJSON<>("test.txt")).build();
        cache.load().load(cache);
        cache.expire("1", 100000L);
        new InnerCachePersist<>(cache);
    }
}
