package com.hjh.cache.support.interceptor;

import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.support.interceptor.common.CacheCostInterceptor;
import com.hjh.cache.support.interceptor.evict.CacheEvictInterceptor;
import com.hjh.cache.support.interceptor.expire.CacheExpireInterceptor;

/**
 * @author hongjinhui
 * 2022/3/28
 */

public class CacheInterceptors {
    public static ICacheInterceptor evict() {
        return new CacheEvictInterceptor();
    }

    public static ICacheInterceptor expire() {
        return new CacheExpireInterceptor();
    }

    public static ICacheInterceptor cost() {
        return new CacheCostInterceptor();
    }
}
