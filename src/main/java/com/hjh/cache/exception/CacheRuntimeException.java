package com.hjh.cache.exception;

/**
 * @author hongjinhui
 * 2022/3/24
 */

public class CacheRuntimeException extends RuntimeException{
    public CacheRuntimeException() {}
    public CacheRuntimeException(String msg) {
        super(msg);
    }
}
