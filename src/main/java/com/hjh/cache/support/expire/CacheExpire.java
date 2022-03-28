package com.hjh.cache.support.expire;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheExpire;
import com.sun.org.apache.bcel.internal.generic.LMUL;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author hongjinhui
 * 2022/3/26
 */

public class CacheExpire<K, V> implements ICacheExpire<K, V> {

    /**
     * 单次删除次数限制
     */
    private static final int LIMIT = 100;

    private Map<K, Long> expireMap = new ConcurrentHashMap<>(128);

    private final ICache<K, V> cache;

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpire(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    private class ExpireThread implements Runnable{
        @Override
        public void run() {
            if (expireMap == null || expireMap.size() == 0) {
                return;
            }
            int count = 0;
            for (Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if (count >= LIMIT) {
                    return;
                }
                expireKey(entry.getKey(), entry.getValue());
                ++count;
            }
        }
    }

    @Override
    public void expire(K key, Long expireTime) {
        this.expireAt(key, System.currentTimeMillis() + expireTime);
    }

    private void expireAt(K key, Long expireAt) {
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if (keyList == null) {
            return;
        }
        if (keyList.size() <= expireMap.size()) {
            for (K key : keyList) {
                expireKey(key, expireMap.get(key));
            }
        } else {
            for (Map.Entry<K, Long> entry : expireMap.entrySet()) {
                expireKey(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Long expireTime(K key) {
        return expireMap.get(key);
    }

    @Override
    public void expireKey(K key, Long expireAt) {
        if (expireAt == null) {
            return;
        }
        Long currentTime = System.currentTimeMillis();
        if (currentTime >= expireAt) {
            expireMap.remove(key);
            cache.remove(key);
        }
    }


}
