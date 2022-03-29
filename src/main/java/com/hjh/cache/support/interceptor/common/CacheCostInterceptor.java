package com.hjh.cache.support.interceptor.common;

import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.api.ICacheInterceptorContext;
import com.hjh.cache.api.ICacheSlowListener;
import com.hjh.cache.api.ICacheSlowListenerContext;
import com.hjh.cache.support.listener.slow.CacheSlowListenerContext;

/**
 * 花费操作拦截器
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheCostInterceptor<K, V> implements ICacheInterceptor<K, V> {
    @Override
    public void before(ICacheInterceptorContext<K, V> context) {

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        ICacheSlowListenerContext<K, V> slowListenerContext = CacheSlowListenerContext.<K, V>getInstance()
                .methodName(context.method().getName())
                .params(context.params())
                .result(context.result())
                .startMills(context.startMills())
                .endMills(context.endMills())
                .costMills(context.endMills() - context.startMills());

        // 如果花费的时间大于阙值则监听
        for (ICacheSlowListener<K, V> cacheSlowListener : context.cache().slowListeners()) {
            if (slowListenerContext.costMills() > cacheSlowListener.showerThanMills()) {
                cacheSlowListener.listen(slowListenerContext);
            }
        }
    }
}
