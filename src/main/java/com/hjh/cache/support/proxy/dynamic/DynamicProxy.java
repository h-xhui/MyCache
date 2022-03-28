package com.hjh.cache.support.proxy.dynamic;

import com.hjh.cache.api.ICache;
import com.hjh.cache.support.proxy.api.ICacheProxy;
import com.hjh.cache.support.proxy.bs.CacheProxyBs;
import com.hjh.cache.support.proxy.bs.CacheProxyBsContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *
 * @author hongjinhui
 * 2022/3/28
 */

public class DynamicProxy implements InvocationHandler, ICacheProxy {
    private ICache target;

    public DynamicProxy(ICache target) {
        this.target = target;
    }

    @Override
    public Object proxy() {
        InvocationHandler handler = new DynamicProxy(target);
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CacheProxyBsContext context = CacheProxyBsContext.newInstance()
                .method(method)
                .params(args)
                .target(target);
        return CacheProxyBs.getInstance().context(context).execute();
    }
}
