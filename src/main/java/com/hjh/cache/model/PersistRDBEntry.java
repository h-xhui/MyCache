package com.hjh.cache.model;

/**
 * rdb持久化实体
 *
 * @author hongjinhui
 * 2022/3/27
 */

public class PersistRDBEntry<K, V> {
    private K key;
    private V value;
    private Long expireTime;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "PersistRDBEntry{" +
                "key=" + key +
                ", value=" + value +
                ", expireTime=" + expireTime +
                '}';
    }
}
