package com.hjh.cache.support.interceptor.aof;

import com.alibaba.fastjson.JSON;
import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheInterceptor;
import com.hjh.cache.api.ICacheInterceptorContext;
import com.hjh.cache.api.ICachePersist;
import com.hjh.cache.model.PersistAOFEntry;
import com.hjh.cache.support.persist.CachePersistAOF;
import org.apache.log4j.Logger;

/**
 * aof持久化拦截器
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheAofInterceptor<K, V> implements ICacheInterceptor<K ,V> {
    private final Logger log = Logger.getLogger(CacheAofInterceptor.class);
    @Override
    public void before(ICacheInterceptorContext<K, V> context) {
        ICache<K, V> cache = context.cache();
        ICachePersist<K, V> persist = cache.persist();
        if (persist instanceof CachePersistAOF) {
            CachePersistAOF<K, V> cachePersistAOF = (CachePersistAOF<K, V>) persist;
            PersistAOFEntry aofEntry = new PersistAOFEntry();
            aofEntry.setMethodName(context.method().getName());
            aofEntry.setParams(context.params());
            String content = JSON.toJSONString(aofEntry);
            log.debug("[AOF]开始追加文件内容:{" + content +"}");
            cachePersistAOF.append(content);
            log.debug("[AOF]完成追加文件内容:{" + content +"}");
        }
    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {

    }
}
