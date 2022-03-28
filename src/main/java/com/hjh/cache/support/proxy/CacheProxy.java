package com.hjh.cache.support.proxy;

import com.hjh.cache.api.ICache;
import com.hjh.cache.support.proxy.None.NoneProxy;
import com.hjh.cache.support.proxy.api.ICacheProxy;
import com.hjh.cache.support.proxy.cglib.CglibProxy;
import com.hjh.cache.support.proxy.dynamic.DynamicProxy;

import java.lang.reflect.Proxy;

/**
 * 缓存代理
 *
 * @author hongjinhui
 * 2022/3/28
 */

public final class CacheProxy {

    @SuppressWarnings("all")
    public static <K, V> ICache<K, V> getProxy(ICache<K, V> cache) {
        if (cache == null) {
            return (ICache<K, V>) new NoneProxy(cache).proxy();
        }

        final Class clazz = cache.getClass();
        if (clazz.isInterface() || Proxy.isProxyClass(clazz)) {
            return (ICache<K, V>) new DynamicProxy(cache).proxy();
        }
        return (ICache<K, V>) new CglibProxy(cache).proxy();
    }
}
