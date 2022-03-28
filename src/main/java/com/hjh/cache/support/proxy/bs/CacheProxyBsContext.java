package com.hjh.cache.support.proxy.bs;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.annotation.CacheInterceptor;
import com.hjh.cache.support.proxy.api.ICacheProxy;
import com.hjh.cache.support.proxy.api.ICacheProxyBsContext;

import java.lang.reflect.Method;

/**
 * 缓存代理引导类
 *
 * @author hongjinhui
 * 2022/3/28
 */

public class CacheProxyBsContext implements ICacheProxyBsContext {
    private CacheInterceptor cacheInterceptor;
    private ICache target;
    private Object[] params;
    private Method method;

    public static CacheProxyBsContext newInstance() {
        return new CacheProxyBsContext();
    }

    @Override
    public CacheInterceptor cacheInterceptor() {
        return this.cacheInterceptor;
    }

    @Override
    public ICache target() {
        return this.target;
    }

    public CacheProxyBsContext target(ICache target) {
        this.target = target;
        return this;
    }

    @Override
    public Object[] params() {
        return this.params;
    }

    public CacheProxyBsContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Method method() {
        return this.method;
    }

    public CacheProxyBsContext method(Method method) {
        this.method = method;
        this.cacheInterceptor = method.getAnnotation(CacheInterceptor.class);
        return this;
    }

    @Override
    public Object process() throws Throwable {
        return this.method.invoke(target, params);
    }
}
