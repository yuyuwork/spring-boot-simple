package org.palm.spring.boot.simple.redis.service.impl;

import org.palm.spring.boot.simple.redis.enums.ExpireEnum;
import org.palm.spring.boot.simple.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作缓存数据服务实现
 *
 * @author
 * @date 2018/12/14 20:28
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        ValueOperations<String, Object> valueOperation = redisTemplate.opsForValue();
        valueOperation.set(key, value);
    }

    @Override
    public void setWithExpire(String key, Object value, long time, TimeUnit timeUnit) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(key);
        boundValueOperations.set(value);
        boundValueOperations.expire(time,timeUnit);
    }

    @Override
    public <K> K get(String key) {
        ValueOperations<String, Object> valueOperation = redisTemplate.opsForValue();
        return (K) valueOperation.get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void addToListLeft(String listKey, ExpireEnum expireEnum, Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.leftPushAll(values);
        //设置过期时间
        boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
    }

    @Override
    public void addToListRight(String listKey, ExpireEnum expireEnum, Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过期时间
        boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
    }

    @Override
    public List<Object> rangeList(String listKey, long start, long end) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }

    @Override
    public void addToSet(String setKey, Object... values) {
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(setKey, values);
    }

    @Override
    public Boolean isSetMember(String setKey, Object value) {
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.isMember(setKey, value);
    }

    @Override
    public void removeFromSet(String setKey, Object... values) {
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.remove(setKey, values);
    }

    @Override
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    @Override
    public Long incr(String key, ExpireEnum expireEnum) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        //初始设置过期时间
        if ((null == increment || increment.longValue() == 0) && expireEnum.getTime() > 0) {
            entityIdCounter.expire(expireEnum.getTime(), expireEnum.getTimeUnit());
        }
        return increment;
    }

    @Override
    public void set(String key, int value, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expireAt(expireTime);
    }

    @Override
    public void set(String key, int value, long timeout, TimeUnit unit) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expire(timeout, unit);
    }

    @Override
    public long generate(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    @Override
    public long generate(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.incrementAndGet();
    }

    @Override
    public long generate(String key, int increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.addAndGet(increment);
    }

    @Override
    public long generate(String key, int increment, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.addAndGet(increment);
    }

}
