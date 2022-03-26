package com.hjh.cache.support.interceptor.evict;

import com.hjh.cache.api.ICacheEvict;
import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.api.ICacheInterceptorContext;

import java.lang.reflect.Method;

/**
 * 淘汰策略拦截器
 *
 * @author hongjinhui
 * 2022/3/25
 */

public class CacheEvictInterceptor<K, V> implements ICacheInterceptor<K, V> {
    @Override
    public void before(ICacheInterceptorContext<K, V> context) {

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        ICacheEvict<K, V> evict = context.cache().evict();
        Method method = context.method();
        final K key = (K) context.params()[0];
        if ("remove".equals(method.getName())) {
            evict.removeKey(key);
        } else {
            evict.updateKey(key);
        }
    }
}
