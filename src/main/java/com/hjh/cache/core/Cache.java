package com.hjh.cache.core;

import com.hjh.cache.api.*;
import com.hjh.cache.api.annotation.CacheInterceptor;
import com.hjh.cache.constant.enums.CacheRemoveType;
import com.hjh.cache.exception.CacheRuntimeException;
import com.hjh.cache.support.evict.CacheEvictContext;
import com.hjh.cache.support.expire.CacheExpire;
import com.hjh.cache.support.listener.remove.CacheRemoveListener;
import com.hjh.cache.support.listener.remove.CacheRemoveListenerContext;
import com.hjh.cache.support.listener.remove.CacheRemoveListeners;
import com.hjh.cache.support.listener.slow.CacheSlowListeners;
import com.hjh.cache.support.load.CacheLoadNone;
import com.hjh.cache.support.persist.InnerCachePersist;
import com.hjh.cache.support.proxy.CacheProxy;

import java.lang.annotation.Inherited;
import java.util.*;

/**
 * @author hongjinhui
 * 2022/3/24
 */

public class Cache<K, V> implements ICache<K, V> {
    private Map<K, V> map;
    private int sizeLimit;
    private ICacheEvict<K, V> evict;
    private ICacheExpire<K, V> expire;
    private ICacheLoad<K, V> load;
    private ICachePersist<K, V> persist;
    private List<ICacheRemoveListener<K, V>> removeListeners;
    private List<ICacheSlowListener<K, V>> slowListeners;

    public void init() {
        if (expire == null) {
            expire = new CacheExpire<>(this);
        }


        if (removeListeners == null) {
            removeListeners = CacheRemoveListeners.defaults();
        }

        if (slowListeners == null) {
            slowListeners = CacheSlowListeners.none();
        }

        if (load != null) {
            load.load(this);
        }

        if (persist != null) {
            new InnerCachePersist<>(this);
        }

    }

    /**
     * 设置map的实现
     * @param map
     * @return
     */
    public Cache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    /**
     * 设置大小限制
     * @param sizeLimit
     * @return
     */
    public Cache<K, V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }


    @Override
    public ICacheEvict<K, V> evict() {
        return this.evict;
    }

    /**
     * 设置淘汰策略
     * @param cacheEvict
     * @return
     */
    public Cache<K, V> evict(ICacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }

    @Override
    public ICacheExpire<K, V> expire() {
        return this.expire;
    }

    @Override
    public ICacheLoad<K, V> load() {
        return this.load;
    }

    @Override
    public ICachePersist<K, V> persist() {
        return this.persist;
    }

    @Override
    public List<ICacheRemoveListener<K, V>> removeListeners() {
        return this.removeListeners;
    }

    @Override
    public List<ICacheSlowListener<K, V>> slowListeners() {
        return this.slowListeners;
    }

    public Cache<K, V> persist(ICachePersist<K, V> persist) {
        this.persist = persist;
        return this;
    }

    public Cache<K, V> load(ICacheLoad<K, V> load){
        this.load = load;
        return this;
    }

    public Cache<K, V> removeListeners(List<ICacheRemoveListener<K, V>> removeListeners) {
        this.removeListeners = removeListeners;
        return this;
    }

    public Cache<K, V> slowListeners(List<ICacheSlowListener<K, V>> slowListeners) {
        this.slowListeners = slowListeners;
        return this;
    }

    @Override
    public ICache<K, V> expire(K key, Long expireTime) {
        CacheProxy.getProxy(this).expireAt(key, System.currentTimeMillis() + expireTime);
        return this;
    }

    @Override
    @CacheInterceptor(aof = true)
    public ICache<K, V> expireAt(K key, Long expireAt) {
        this.expire.expireAt(key, expireAt);
        return this;
    }

    @Override
    @CacheInterceptor(refresh = true)
    public int size() {
        return map.size();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @CacheInterceptor(evict = true)
    public V get(Object key) {
        expire.refreshExpire(Collections.singletonList((K) key));
        return map.get(key);
    }

    @Override
    @CacheInterceptor(evict = true, aof = true)
    public V put(K key, V value) {
        CacheEvictContext<K, V> context  = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        ICacheEntry<K, V> evictEntry = evict.evict(context);

        if (evictEntry != null) {
            // 淘汰监听
            CacheRemoveListenerContext<K, V> removeListenerContext = CacheRemoveListenerContext.<K, V>getInstance()
                    .key(evictEntry.key())
                    .value(evictEntry.value())
                    .type(CacheRemoveType.EVICT.getMessage());
            for (ICacheRemoveListener<K, V> removeListener : removeListeners) {
                removeListener.listen(removeListenerContext);
            }
        }

        // 防止没有缓存策略时，超出限制
        if (this.size() >= sizeLimit) {
            throw new CacheRuntimeException("缓存已满，数据添加失败");
        }

        return map.put(key, value);
    }

    @Override
    @CacheInterceptor(evict = true, aof = true)
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    @CacheInterceptor(aof = true)
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    @CacheInterceptor(refresh = true, aof = true)
    public void clear() {
        map.clear();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Collection<V> values() {
        return map.values();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
