package com.hjh.cache.api;

import com.hjh.cache.core.Cache;

import java.util.List;
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
     * 定时删除策略
     * @return
     */
    ICacheExpire<K, V> expire();


    /**
     * 设置键过期
     * @param key
     * @param expireTime
     * @return
     */
    ICache<K, V> expire(K key, Long expireTime);

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

    /**
     * 删除监听器列表
     * @return
     */
    List<ICacheRemoveListener<K, V>> removeListeners();

    /**
     * 满操作监听器
     * @return
     */
    List<ICacheSlowListener<K, V>> slowListeners();
}
