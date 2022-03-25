package com.hjh.cache.api;

/**
 * @author 洪锦辉
 * 2022/3/24
 */

public interface ICacheEvict<K, V> {
    /**
     * 淘汰策略
     * @param context
     * @return 被移除得实体，没有则返回null
     */
    ICacheEntry<K, V> evict(final ICacheEvictContext<K, V> context);

    /**
     * 更新key的消息
     * @param key
     */
    void updateKey(final int key);

    /**
     * 移除key
     * @param key
     */
    void removeKey(final int key);

}
