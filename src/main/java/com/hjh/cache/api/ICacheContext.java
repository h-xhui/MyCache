package com.hjh.cache.api;

import java.util.Map;

/**
 * 缓存上下文
 * @author 洪锦辉
 * 2022/3/24
 */

public interface ICacheContext<K, V> {

    Map<K, V> map();

    /**
     * 大小限制
     * @return
     */
    int size();

    /**
     * 淘汰策略
     * @return
     */
    ICacheEvict<K, V> cacheEvict();
}
