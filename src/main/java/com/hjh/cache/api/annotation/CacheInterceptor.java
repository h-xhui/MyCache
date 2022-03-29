package com.hjh.cache.api.annotation;

import java.lang.annotation.*;

/**
 * @author hongjinhui
 * 2022/3/25
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInterceptor {
    /**
     * 是否执行淘汰策略
     * 主要用于LRU/LFU的淘汰策略
     * @return
     */
    boolean evict() default false;

    /**
     * 是否启用刷新
     * @return
     */
    boolean refresh() default false;

    /**
     * 通用拦截器
     * @return
     */
    boolean common() default true;
}
