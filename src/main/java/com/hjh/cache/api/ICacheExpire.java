package com.hjh.cache.api;

import java.util.Collection;

/**
 * @author hongjinhui
 * 2022/3/26
 */

public interface ICacheExpire<K, V> {

    /**
     * 指定过期时间
     * @param key
     * @param expireTime
     */
    void expire(K key, Long expireTime);

    void expireAt(K key, Long expireAt);

    /**
     * 惰性删除中所需要处理的keys
     * @param keyList
     */
    void refreshExpire(Collection<K> keyList);

    /**
     * 获取键过期时间
     * @param key
     * @return
     */
    Long expireTime(K key);

    void expireKey(K key, Long expireAt);
}
