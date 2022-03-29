package com.hjh.cache.support.persist;

import com.hjh.cache.api.ICache;
import com.hjh.cache.utils.FileUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * aof方式持久化
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CachePersistAOF<K, V> extends CachePersistAdaptor<K, V> {
    private final Logger log = Logger.getLogger(CachePersistAOF.class);
    private final List<String> buffer = new ArrayList<>();
    private final String fileName;
    private final String dbPath;

    public CachePersistAOF(String fileName) {
        this.fileName = fileName;
        this.dbPath = FileUtil.getCurrentProjectPath() + "\\db\\" + fileName;
    }

    @Override
    public void persist(ICache<K, V> cache) {
        if (buffer.size() == 0) {
            return;
        }
        log.info("开始aof持久化");

        FileUtil.makeFile(dbPath);
        for (String line : buffer) {
            FileUtil.fileLinesWrite(dbPath, line, true);
        }
        buffer.clear();

        log.info("完成aof持久化");
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
        return TimeUnit.SECONDS;
    }

    public void append(String json) {
        if (json != null) {
            buffer.add(json);
        }
    }
}
