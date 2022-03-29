package com.hjh.cache.support.listener.remove;

import com.hjh.cache.api.ICacheRemoveListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheRemoveListeners {
    public static <K, V> List<ICacheRemoveListener<K, V>> defaults() {
        List<ICacheRemoveListener<K, V>> listeners = new ArrayList<>(3);
        listeners.add(new CacheRemoveListener<>());

        return listeners;
    }
}
