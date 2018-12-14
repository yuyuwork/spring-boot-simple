package org.palm.spring.boot.simple.redis.test;

import com.sun.jmx.snmp.Timestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.palm.spring.boot.simple.redis.RedisApplication;
import org.palm.spring.boot.simple.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    private static final String REDIS_WEBSOCKET_USER_SET="mq-websocket:websocket_user_set";

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redisSetTest(){
        System.out.println("当前时间是："+new Timestamp(System.currentTimeMillis()));

        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(REDIS_WEBSOCKET_USER_SET,"hahaha","admin");

        //1. 遍历
        Set<Object> set = opsForSet.members(REDIS_WEBSOCKET_USER_SET);
        System.out.println("缓存集合数据："+set);

        //2. 判断是否是set成员
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"admin"));
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"zifangsky"));

        //3. 移除某个值
        opsForSet.remove(REDIS_WEBSOCKET_USER_SET,"admin");

        //4. 再次判断
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"admin"));
    }

}
