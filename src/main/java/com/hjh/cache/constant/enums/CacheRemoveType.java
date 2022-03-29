package com.hjh.cache.constant.enums;

/**
 * @author hongjinhui
 * 2022/3/29
 */

public enum CacheRemoveType {
    EVICT("evict", "淘汰"),
    EXPIRE("expire", "过期")
    ;

    private String code;
    private String message;

    CacheRemoveType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CacheRemoveType{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
