package com.hjh.cache.support.listener.remove;

import com.hjh.cache.api.ICacheRemoveListener;
import com.hjh.cache.api.ICacheRemoveListenerContext;
import org.apache.log4j.Logger;

/**
 * 删除监听器,主要监听淘汰的删除和过期删除
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheRemoveListener<K, V> implements ICacheRemoveListener<K, V> {
    private final Logger logger = Logger.getLogger(CacheRemoveListener.class);
    @Override
    public void listen(ICacheRemoveListenerContext<K, V> context) {
        logger.info("Remove key:{" + context.key() + "} ,value:{" + context.value() + "} ,type:{" + context.type()+ "}");
    }
}
