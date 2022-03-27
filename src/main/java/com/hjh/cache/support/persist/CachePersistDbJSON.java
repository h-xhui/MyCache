package com.hjh.cache.support.persist;

import com.alibaba.fastjson.JSON;
import com.hjh.cache.api.ICache;
import com.hjh.cache.model.PersistRDBEntry;
import com.hjh.cache.utils.FileUtil;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 已以json的形式rdb持久化
 *
 * @author hongjinhui
 * 2022/3/27
 */

public class CachePersistDbJSON<K, V> extends CachePersistAdaptor<K, V> {

    private final String dbPath;
    private final String fileName;
    public CachePersistDbJSON(String fileName) {
        this.fileName = fileName;
        this.dbPath = FileUtil.getCurrentProjectPath() + "\\" + fileName;
    }
    @Override
    public void persist(ICache<K, V> cache) {
        FileUtil.makeFile(dbPath);
        FileUtil.clearFile(dbPath);
        for (Map.Entry<K, V> entry : cache.entrySet()) {
            PersistRDBEntry<K, V> persistRDBEntry = new PersistRDBEntry<>();
            persistRDBEntry.setKey(entry.getKey());
            persistRDBEntry.setValue(entry.getValue());
            persistRDBEntry.setExpireTime(cache.expire() == null? null:cache.expire().expireTime(entry.getKey()));
            String line = JSON.toJSONString(persistRDBEntry);
            FileUtil.fileLinesWrite(dbPath, line, true);
        }
    }

    @Override
    public long delay() {
        return 1;
    }

    @Override
    public long period() {
        return 1;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }

}
