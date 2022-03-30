package com.hjh.cache.support.evict.struct;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hongjinhui
 * 2022/3/30
 */

public class LruLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private boolean removeFlag;
    private Map.Entry<K, V> oldestEntry;

    public LruLinkedHashMap() {
        // 将accessOrder设置为true是为了按照访问顺序排序
        super(16, 0.75f, true);
    }

    public void setRemoveFlag(boolean removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Map.Entry<K, V> getOldestEntry() {
        return oldestEntry;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        this.oldestEntry = eldest;
        return removeFlag;
    }
}
