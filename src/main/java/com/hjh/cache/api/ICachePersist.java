package com.hjh.cache.api;

import java.util.concurrent.TimeUnit;

/**
 * 持久化
 * @author hongjinhui
 * 2022/3/26
 */

public interface ICachePersist<K, V> {

    /**
     * 持久化缓存消息
     * @param cache
     */
    void persist(ICache<K, V> cache);

    /**
     * 延迟时间
     * @return
     */
    long delay();

    /**
     * 间隔时间
     * @return
     */
    long period();

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit();
}
