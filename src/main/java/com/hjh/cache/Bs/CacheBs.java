package com.hjh.cache.Bs;

import com.hjh.cache.api.*;
import com.hjh.cache.core.Cache;
import com.hjh.cache.support.evict.CacheEvicts;
import com.hjh.cache.support.expire.CacheExpire;
import com.hjh.cache.support.listener.remove.CacheRemoveListeners;
import com.hjh.cache.support.listener.slow.CacheSlowListeners;
import com.hjh.cache.support.load.CacheLoadAOF;
import com.hjh.cache.support.load.CacheLoadNone;
import com.hjh.cache.support.load.CacheLoads;
import com.hjh.cache.support.persist.CachePersistAOF;
import com.hjh.cache.support.persist.CachePersistDbJSON;
import com.hjh.cache.support.persist.CachePersists;
import com.hjh.cache.support.persist.InnerCachePersist;
import com.hjh.cache.support.proxy.CacheProxy;
import com.hjh.cache.support.proxy.api.ICacheProxy;

import java.util.HashMap;
import java.util.List;
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
    private List<ICacheRemoveListener<K, V>> removeListeners;
    private List<ICacheSlowListener<K, V>> slowListeners;

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

    public CacheBs<K, V> removeListeners(List<ICacheRemoveListener<K, V>> removeListeners) {
        this.removeListeners = removeListeners;
        return this;
    }

    public CacheBs<K, V> slowListeners(List<ICacheSlowListener<K, V>> slowListeners) {
        this.slowListeners = slowListeners;
        return this;
    }

    public ICache<K, V> build() {
        Cache<K, V> cache = new Cache<>();
        cache.map(map);
        cache.sizeLimit(size);
        cache.evict(evict);
        cache.load(load);
        cache.persist(persist);
        cache.removeListeners(removeListeners);
        cache.slowListeners(slowListeners);
        cache.init();
        return CacheProxy.getProxy(cache);
    }

    public static void main(String[] args) throws InterruptedException {
        ICache<String, String> cache = CacheBs.<String, String>newInstance()
                .size(3)
                .evict(CacheEvicts.LFU())
                .removeListeners(CacheRemoveListeners.defaults())
                .slowListeners(CacheSlowListeners.defaults())
                //.load(CacheLoads.loadByRDB("data.rdb"))
                //.persist(CachePersists.dbJSON("data.rdb"))
                .build();
        cache.put("2", "2");
        cache.get("2");
        cache.put("1", "1");
        cache.get("1");
        cache.get("2");
        cache.put("3", "3");
        cache.get("3");
        cache.remove("3");
        cache.put("4", "4");
        cache.put("5", "5");
        //cache.expire("4", 90000L);
        System.out.println(cache.entrySet());
    }
}
