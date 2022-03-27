package com.hjh.cache.support.load;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheLoad;

/**
 * 加载类测试
 *
 * @author hongjinhui
 * 2022/3/26
 */

public class CacheLoadNone implements ICacheLoad<String, String> {
    @Override
    public void load(ICache<String, String> cache) {
        cache.put("1", "1");
        cache.put("2", "2");
    }
}
