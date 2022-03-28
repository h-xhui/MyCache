package com.hjh.cache.support.proxy.cglib;

import com.hjh.cache.api.ICache;
import com.hjh.cache.support.proxy.api.ICacheProxy;
import com.hjh.cache.support.proxy.api.ICacheProxyBsContext;
import com.hjh.cache.support.proxy.bs.CacheProxyBs;
import com.hjh.cache.support.proxy.bs.CacheProxyBsContext;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author hongjinhui
 * 2022/3/28
 */

public class CglibProxy implements MethodInterceptor, ICacheProxy {
    private ICache target;

    public CglibProxy(ICache cache) {
        this.target = cache;
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ICacheProxyBsContext context = CacheProxyBsContext.newInstance().method(method).params(objects).target(target);
        return CacheProxyBs.getInstance().context(context).execute();
    }
}
