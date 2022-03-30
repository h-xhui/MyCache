package com.hjh.cache.support.evict;

import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheEntry;
import com.hjh.cache.api.ICacheEvictContext;
import com.hjh.cache.exception.CacheRuntimeException;
import com.hjh.cache.model.CacheEntry;
import com.hjh.cache.support.evict.struct.LfuNode;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * lfu算法-最近最不常使用
 *
 * @author hongjinhui
 * 2022/3/30
 */

public class CacheEvictLFU<K, V> extends AbstractCacheEvict<K, V> {
    private final Logger log = Logger.getLogger(CacheEvictLFU.class);
    private final LfuNode<K> head = new LfuNode<>(0);
    private final LfuNode<K> tail = new LfuNode<>(0);
    private final Map<K, LfuNode<K>> map = new HashMap<>();

    public CacheEvictLFU() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            if (head.next() == tail) {
                throw new CacheRuntimeException("缓存数据为空，淘汰失败");
            }
            K evictKey = head.next().removeOne();
            // 注意，要删除map中的key
            map.remove(evictKey);
            V evictValue = cache.remove(evictKey);

            return new CacheEntry<>(evictKey, evictValue);
        }
        return null;
    }

    @Override
    public void updateKey(K key) {
        log.debug("key:{" + key + "}使用次数加一");
        if (!map.containsKey(key)) {
            LfuNode<K> now = head.next();
            if (now.count() == 1) {
                now.put(key);
                map.put(key, head.next());
            } else {
                LfuNode<K> newNode = new LfuNode<>(1);
                newNode.put(key);
                head.setNext(newNode);
                newNode.setNext(now);
                now.setPrev(newNode);
                newNode.setPrev(head);
                map.put(key, newNode);
            }
        } else {
            LfuNode<K> now = map.get(key);
            LfuNode<K> next = now.next();
            LfuNode<K> prev = now.prev();
            Integer newCount = now.count() + 1;
            now.remove(key);
            if (now.size() == 0) {
                now = prev;
            }
            if (newCount == next.count()) {
                next.put(key);
                map.put(key, next);
            } else {
                LfuNode<K> newNode = new LfuNode<>(newCount);
                newNode.put(key);
                map.put(key, newNode);
                now.setNext(newNode);
                newNode.setNext(next);
                next.setNext(newNode);
                newNode.setPrev(now);
            }
        }
    }

    @Override
    public void removeKey(K key) {
        log.debug(key + "删除");
        LfuNode<K> now = map.get(key);
        map.remove(key);
        now.remove(key);
    }
}
