package com.hjh.cache.model;

import java.util.Arrays;

/**
 * aof持久化实体类
 *
 * @author hongjinhui
 * 2022/3/29
 */

public class PersistAOFEntry {
    private String methodName;
    private Object[] params;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "PersistAOFEntry{" +
                "methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
