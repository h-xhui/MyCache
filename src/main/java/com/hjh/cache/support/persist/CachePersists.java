package com.hjh.cache.support.persist;

import com.hjh.cache.api.ICacheLoad;
import com.hjh.cache.api.ICachePersist;

/**
 * 持久化消息
 *
 * @author hongjinhui
 * 2022/3/27
 */

public class CachePersists<K, V> {
    public static <K, V> ICachePersist<K, V> none() {
        return new CachePersistNone<>();
    }

    public static <K, V> ICachePersist<K, V> dbJSON(String fileName) {
        return new CachePersistDbJSON<>(fileName);
    }

    public static <K, V> ICachePersist<K, V> aof(String fileName) {
        return new CachePersistAOF<>(fileName);
    }
}
