package com.hjh.cache.support.listener.slow;

import com.hjh.cache.api.ICacheSlowListenerContext;

/**
 * 满操作监听器上下文
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheSlowListenerContext<K, V> implements ICacheSlowListenerContext<K, V> {
    private String methodName;
    private Object[] params;
    private Object result;
    private long startMills;
    private long endMills;
    private long costMills;

    public static <K, V> CacheSlowListenerContext<K, V> getInstance() {
        return new CacheSlowListenerContext<>();
    }

    @Override
    public String methodName() {
        return methodName;
    }

    public CacheSlowListenerContext<K, V> methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public CacheSlowListenerContext<K, V> params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Object result() {
        return result;
    }

    public CacheSlowListenerContext<K, V> result(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public long startMills() {
        return startMills;
    }

    public CacheSlowListenerContext<K, V> startMills(long startMills) {
        this.startMills = startMills;
        return this;
    }

    @Override
    public long endMills() {
        return endMills;
    }

    public CacheSlowListenerContext<K, V> endMills(long endMills) {
        this.endMills = endMills;
        return this;
    }

    @Override
    public long costMills() {
        return costMills;
    }

    public CacheSlowListenerContext<K, V> costMills(long costMills) {
        this.costMills = costMills;
        return this;
    }
}
