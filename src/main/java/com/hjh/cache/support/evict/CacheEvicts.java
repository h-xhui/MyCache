package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICacheEvict;

/**
 * 淘汰策略集合
 *
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheEvicts {
    private CacheEvicts() {}

    /**
     * fifo
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> ICacheEvict<K, V> FIFO() {
        return new CacheEvictFIFO<>();
    }

    public static <K, V> ICacheEvict<K, V> None() {
        return new CacheEvictNone<>();
    }

    public static <K, V> ICacheEvict<K, V> LRU() {
        return new CacheEvictLRU<>();
    }

    public static <K, V> ICacheEvict<K, V> LRUPlus() {
        return new CacheEvictLRUPlus<>();
    }

    public static <K, V> ICacheEvict<K, V> LRU_2() {
        return new CacheEvictLRU_2<>();
    }

    public static <K, V> ICacheEvict<K, V> LFU() {
        return new CacheEvictLFU<>();
    }
}
