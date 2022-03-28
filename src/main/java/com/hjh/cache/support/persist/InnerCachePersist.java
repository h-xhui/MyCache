package com.hjh.cache.support.persist;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICachePersist;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 透明的方式实现持久化
 *
 * @author hongjinhui
 * 2022/3/27
 */
public class InnerCachePersist<K, V> {
    private Logger logger = Logger.getLogger(InnerCachePersist.class);
    private ICache<K, V> cache;
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public InnerCachePersist(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    private void init() {
        ICachePersist<K, V> persist = cache.persist();
        if (persist == null || persist.getClass() == CachePersistNone.class) {
            return;
        }
        EXECUTOR_SERVICE.scheduleAtFixedRate(()->{
            try {
                logger.info("持久化开始");
                persist.persist(cache);
                logger.info("持久化成功");
            } catch (Exception e) {
                logger.error("持久化失败", e);
            }
        }, persist.delay(), persist.period(), persist.timeUnit());
    }

}
