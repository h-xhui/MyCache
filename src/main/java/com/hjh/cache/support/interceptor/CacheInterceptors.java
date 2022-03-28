package com.hjh.cache.support.interceptor;

import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.support.interceptor.evict.CacheEvictInterceptor;

/**
 * @author hongjinhui
 * 2022/3/28
 */

public class CacheInterceptors {
    public static ICacheInterceptor evict() {
        return new CacheEvictInterceptor();
    }
}
