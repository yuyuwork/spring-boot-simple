package org.palm.spring.boot.simple.caching.redis.service.impl;

import org.palm.spring.boot.simple.caching.redis.service.RedisService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "caching")
public class RedisServiceImpl implements RedisService {

    @Override
    @CachePut(key = "#cacheValue")
    public String setCacheValue(String cacheValue) {
        //@CachePut(key = "#p0.id")
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
