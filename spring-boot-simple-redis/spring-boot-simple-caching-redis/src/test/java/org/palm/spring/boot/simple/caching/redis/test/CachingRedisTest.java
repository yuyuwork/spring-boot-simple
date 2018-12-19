package org.palm.spring.boot.simple.caching.redis.test;

import com.sun.jmx.snmp.Timestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.palm.spring.boot.simple.caching.redis.CachingRedisApplication;
import org.palm.spring.boot.simple.caching.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CachingRedisApplication.class)
public class CachingRedisTest {

    @Autowired
    private RedisService redisService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisCacheTest(){
        //使用Caching-Redis，修正在@CachePut的key定义(@CachePut(key = "#p0.id"))
        System.out.println("当前时间是："+new Timestamp(System.currentTimeMillis()));

        String setCacheValue=redisService.setCacheValue("002");
        String getCacheValue=redisService.getCacheValue("002");

        System.out.println("设置缓存："+setCacheValue+"，获取缓存："+getCacheValue);
    }

    @Test
    public void nosqlRedisTest(){
        //使用NoSQL数据库-redis
        String key="key001";
        if (!stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.opsForValue().set(key, "hello 001");
            System.out.println("set key success");
        } else {
            //存在则打印之前的value值
            System.out.println("this key = " + stringRedisTemplate.opsForValue().get(key));
        }

        //输出redis中的结果
        System.out.println(stringRedisTemplate.opsForValue().get(key));
        stringRedisTemplate.delete(key);
        System.out.println(stringRedisTemplate.opsForValue().get(key));
    }
}
