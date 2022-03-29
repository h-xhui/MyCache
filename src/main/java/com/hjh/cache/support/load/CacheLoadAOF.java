package com.hjh.cache.support.load;

import com.alibaba.fastjson.JSON;
import com.hjh.cache.api.ICache;
import com.hjh.cache.api.ICacheLoad;
import com.hjh.cache.api.annotation.CacheInterceptor;
import com.hjh.cache.core.Cache;
import com.hjh.cache.model.PersistAOFEntry;
import com.hjh.cache.support.persist.CachePersistAOF;
import com.hjh.cache.utils.FileUtil;
import javafx.scene.paint.Stop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载aof实现
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class CacheLoadAOF<K, V> implements ICacheLoad<K, V> {
    private final String fileName;
    private final String dbPath;

    /**
     * 方法缓存
     */
    private static Map<String, Method> METHOD_MAP = new HashMap<>(16);

    static {
        Method[] methods = Cache.class.getMethods();
        for (Method method : methods) {
            CacheInterceptor cacheInterceptor = method.getAnnotation(CacheInterceptor.class);
            if (cacheInterceptor != null) {
                if (cacheInterceptor.aof()) {
                    METHOD_MAP.put(method.getName(), method);
                }
            }
        }
    }

    public CacheLoadAOF(String fileName) {
        this.fileName = fileName;
        this.dbPath = FileUtil.getCurrentProjectPath() + "\\db\\" + fileName;
    }

    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readFileContent(dbPath);
        for (String line : lines) {
            PersistAOFEntry persistAOFEntry =  JSON.parseObject(line, PersistAOFEntry.class);
            Method method = METHOD_MAP.get(persistAOFEntry.getMethodName());
            try {
                method.invoke(cache, persistAOFEntry.getParams());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
