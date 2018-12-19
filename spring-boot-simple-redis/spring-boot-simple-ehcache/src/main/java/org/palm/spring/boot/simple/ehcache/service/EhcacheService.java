package org.palm.spring.boot.simple.ehcache.service;

public interface EhcacheService {

    /**
     * 设置缓存值
     * @param cacheValue 缓存值
     * @return {String}
     */
    String setCacheValue(String cacheValue);

    /**
     * 获取缓存值
     * @param cacheValue 缓存值
     * @return {String}
     */
    String getCacheValue(String cacheValue);

    /**
     * 删除缓存值
     * @param cacheValue 缓存值
     * @return {String}
     */
    String delCacheValue(String cacheValue);

}
