package com.hjh.cache.Bs;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.core.Cache;
import com.hjh.cache.support.evict.CacheEvicts;
import com.hjh.cache.support.expire.CacheExpire;

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

    public Cache<K, V> build() {
        Cache<K, V> cache = new Cache<>();
        cache.map(map);
        cache.sizeLimit(size);
        cache.evict(evict);

        return cache;
    }

    public static void main(String[] args) {
        Cache<String, String> cache = CacheBs.<String, String>newInstance()
                .size(3).evict(CacheEvicts.FIFO()).build();
        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache.size());
        cache.expire("1", 1000L);
        cache.expire("2", 1000L);
        try {
            Thread.sleep(1010);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.size());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.size());

    }
}
