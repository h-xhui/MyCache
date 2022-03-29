package com.hjh.cache.support.listener.slow;

import com.hjh.cache.api.ICacheSlowListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheSlowListeners {
    public static <K, V> List<ICacheSlowListener<K, V>> none() {
        return new ArrayList<>();
    }

    public static <K, V> List<ICacheSlowListener<K, V>> defaults() {
        List<ICacheSlowListener<K, V>> cacheSlowListeners = new ArrayList<>(3);
        cacheSlowListeners.add(new CacheSlowListener<>(1000L));
        return cacheSlowListeners;
    }
}
