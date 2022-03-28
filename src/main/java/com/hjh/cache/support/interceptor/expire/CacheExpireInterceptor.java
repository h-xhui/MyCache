package com.hjh.cache.support.interceptor.expire;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.api.ICacheInterceptorContext;
import org.apache.log4j.Logger;

/**
 * 过期拦截器
 *
 * @author hongjinhui
 * 2022/3/28
 */

public class CacheExpireInterceptor<K, V> implements ICacheInterceptor<K, V> {
    private final Logger logger = Logger.getLogger(CacheExpireInterceptor.class);
    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
        logger.info("开始刷新");
        ICache<K, V> cache = context.cache();
        cache.expire().refreshExpire(cache.keySet());
        logger.info("刷新完成");
    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {

    }
}
