package com.hjh.cache.api;

import java.util.Map;

/**
 * @author 洪锦辉
 * 2022/3/24
 */

public interface ICache<K, V> extends Map<K, V> {

    /**
     * 淘汰策略
     * @return
     */
    ICacheEvict<K, V> evict();

    /**
     * 删除策略
     * @return
     */
    ICacheExpire<K, V> expire();

    /**
     * 加载消息
     * @return
     */
    ICacheLoad<K, V> load();

    /**
     * 持久化消息
     * @return
     */
    ICachePersist<K, V> persist();
}
