package com.hjh.cache.support.proxy.api;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.annotation.CacheInterceptor;

import java.lang.reflect.Method;

/**
 * @author hongjinhui
 * 2022/3/28
 */

public interface ICacheProxyBsContext {
    /**
     * 拦截器信息
     * @return
     */
    CacheInterceptor cacheInterceptor();

    /**
     * 获取代理对象
     * @return
     */
    ICache target();

    /**
     * 获取参数信息
     * @return
     */
    Object[] params();

    /**
     * 方法信息
     * @return
     */
    Method method();

    /**
     * 方法执行
     * @return
     * @throws Throwable
     */
    Object process() throws Throwable;
}
