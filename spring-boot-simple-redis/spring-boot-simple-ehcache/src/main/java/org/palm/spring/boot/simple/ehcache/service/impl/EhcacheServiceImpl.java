package org.palm.spring.boot.simple.ehcache.service.impl;

import org.palm.spring.boot.simple.ehcache.service.EhcacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "cache")
public class EhcacheServiceImpl implements EhcacheService {

    @Override
    @CachePut(key = "#cacheValue")
    public String setCacheValue(String cacheValue) {
        return "cacheValue："+cacheValue;
    }

    @Override
    @Cacheable(key = "#cacheValue")
    public String getCacheValue(String cacheValue) {
        return "getCacheValue";
    }

    @Override
    @CacheEvict(key = "#cacheValue")
    public String delCacheValue(String cacheValue) {
        return "delCacheValue";
    }

}
