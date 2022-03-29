package com.hjh.cache.support.load;

import com.hjh.cache.api.ICacheLoad;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheLoads {
    public static <K, V> ICacheLoad<K, V> loadByRDB(String fileName) {
        return new CacheLoadRDB<>(fileName);
    }
    public static <K, V> ICacheLoad<K, V> loadByAOF(String fileName) {
        return new CacheLoadAOF<>(fileName);
    }
    public static <K, V> ICacheLoad<K, V> loadByNone() {
        return new CacheLoadNone<>();
    }
}
