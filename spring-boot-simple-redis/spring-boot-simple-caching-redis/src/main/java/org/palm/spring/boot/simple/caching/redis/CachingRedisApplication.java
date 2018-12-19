package org.palm.spring.boot.simple.caching.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * caching-redis启动类
 *
 * @author
 * @date 2018/12/19 10:47
 */
@EnableCaching
@SpringBootApplication
public class CachingRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingRedisApplication.class,args);
    }

}
