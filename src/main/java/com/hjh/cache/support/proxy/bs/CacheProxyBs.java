package com.hjh.cache.support.proxy.bs;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.api.annotation.CacheInterceptor;
import com.hjh.cache.support.interceptor.CacheInterceptorContext;
import com.hjh.cache.support.interceptor.CacheInterceptors;
import com.hjh.cache.support.proxy.api.ICacheProxy;
import com.hjh.cache.support.proxy.api.ICacheProxyBsContext;

/**
 * 代理引导类
 *
 * @author hongjinhui
 * 2022/3/28
 */

public class CacheProxyBs {

    /**
     * 代理上下文信息
     */
    private ICacheProxyBsContext context;

    /**
     * 淘汰拦截器
     */
    private ICacheInterceptor evictInterceptor = CacheInterceptors.evict();

    /**
     * 过期拦截器
     */
    private ICacheInterceptor expireInterceptor = CacheInterceptors.expire();

    public static CacheProxyBs getInstance() {
        return new CacheProxyBs();
    }

    public CacheProxyBs context(ICacheProxyBsContext context) {
        this.context = context;
        return this;
    }

    /**
     * 处理拦截
     * @param cacheInterceptor
     * @param context
     * @param before
     */
    @SuppressWarnings("all")
    public void interceptorHandle(CacheInterceptor cacheInterceptor, CacheInterceptorContext cacheInterceptorContext, boolean before) {
        if (cacheInterceptor != null) {
            // 淘汰策略
            if (cacheInterceptor.evict()) {
                if (before) {
                    evictInterceptor.before(cacheInterceptorContext);
                } else {
                    evictInterceptor.after(cacheInterceptorContext);
                }
            }

            // 刷新过期key
            if (cacheInterceptor.refresh()) {
                if (before) {
                    expireInterceptor.before(cacheInterceptorContext);
                } else {
                    expireInterceptor.after(cacheInterceptorContext);
                }
            }
        }
    }

    @SuppressWarnings("all")
    public Object execute() throws Throwable {
        Long startMills = System.currentTimeMillis();
        ICache cache = context.target();

        // 拦截器上下问信息
        CacheInterceptorContext cacheInterceptorContext = CacheInterceptorContext.newInstance()
                .startMills(startMills)
                .cache(cache)
                .method(context.method())
                .params(context.params());

        // 获取注解信息
        CacheInterceptor cacheInterceptor = context.cacheInterceptor();

        // 方法执行前
        interceptorHandle(cacheInterceptor, cacheInterceptorContext, true);

        // 获取结束时间和结果
        Object result = context.process();
        Long endMills = System.currentTimeMillis();
        cacheInterceptorContext.endMills(endMills).result(result);

        // 方法执行后
        interceptorHandle(cacheInterceptor, cacheInterceptorContext, false);
        return result;
    }
}
