package com.hjh.cache.support.load;

import com.alibaba.fastjson.JSON;
import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheLoad;
import com.hjh.cache.model.PersistRDBEntry;
import com.hjh.cache.utils.FileUtil;

import java.util.List;

/**
 * 通过rdb文件加载
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheLoadRDB<K, V> implements ICacheLoad<K, V> {
    private final String fileName;
    private final String dbPath;

    public CacheLoadRDB(String fileName) {
        this.fileName = fileName;
        this.dbPath = FileUtil.getCurrentProjectPath() + "\\db\\" + fileName;
    }

    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readFileContent(dbPath);
        if (lines.size() == 0) {
            return;
        }

        for (String line : lines) {
            PersistRDBEntry<K, V> rdbEntry = JSON.parseObject(line, PersistRDBEntry.class);
            cache.put(rdbEntry.getKey(), rdbEntry.getValue());
            if (rdbEntry.getExpireTime() != null) {
                cache.expireAt(rdbEntry.getKey(), rdbEntry.getExpireTime());
            }
        }
    }
}
