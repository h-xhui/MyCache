package com.hjh.cache.support.listener.slow;

import com.alibaba.fastjson.JSON;
import com.hjh.cache.api.ICacheSlowListener;
import com.hjh.cache.api.ICacheSlowListenerContext;
import org.apache.log4j.Logger;

/**
 * 慢操作监听器
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheSlowListener<K, V> implements ICacheSlowListener<K, V> {
    private final long showerThanMills;
    private final Logger logger = Logger.getLogger(CacheSlowListener.class);

    public CacheSlowListener(long showerThanMills) {
        this.showerThanMills = showerThanMills;
    }

    @Override
    public void listen(ICacheSlowListenerContext<K, V> context) {
        logger.warn("[Slow] methodName:{" + context.methodName() + "}, params:{" +
                JSON.toJSONString(context.params()) + "}, constTime:{" + context.costMills() + "ms}");
    }

    @Override
    public long showerThanMills() {
        return this.showerThanMills;
    }
}
