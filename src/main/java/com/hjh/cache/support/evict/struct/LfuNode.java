package com.hjh.cache.support.evict.struct;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author hongjinhui
 * 2022/3/30
 */

public class LfuNode<K> {
    private final Logger log = Logger.getLogger(LfuNode.class);
    private final Set<K> set;
    private Integer count;
    private LfuNode<K> next;
    private LfuNode<K> prev;
    public LfuNode(Integer count) {
        this.set = new HashSet<>();
        this.count = count;
    }

    public void put(K key) {
        set.add(key);
    }

    public void remove(K key) {
        set.remove(key);
        if (set.size() == 0) {
            log.debug("次数为：" + count + "节点删除");
            prev.setNext(next);
            next.setPrev(prev);
        }
    }

    public K removeOne() {
        Iterator<K> iterator = set.iterator();
        K evictKey = iterator.next();
        this.remove(evictKey);
        return evictKey;
    }

    public void setNext(LfuNode<K> next) {
        this.next = next;
    }

    public void setPrev(LfuNode<K> prev) {
        this.prev = prev;
    }

    public LfuNode<K> next() {
        return next;
    }

    public LfuNode<K> prev() {
        return prev;
    }

    public Integer count() {
        return count;
    }

    public Integer size() {
        return set.size();
    }
}
