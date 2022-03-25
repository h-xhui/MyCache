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
}
