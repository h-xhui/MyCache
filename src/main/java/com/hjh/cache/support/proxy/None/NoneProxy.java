package com.hjh.cache.support.proxy.None;

import com.hjh.cache.support.proxy.api.ICacheProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 无代理
 *
 * @author hongjinhui
 * 2022/3/28
 */

public class NoneProxy implements InvocationHandler, ICacheProxy {
    private Object target;

    public NoneProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object proxy() {
        return this.target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy, args);
    }
}
