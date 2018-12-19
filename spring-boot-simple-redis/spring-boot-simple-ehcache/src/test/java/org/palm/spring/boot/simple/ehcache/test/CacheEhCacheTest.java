package org.palm.spring.boot.simple.ehcache.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.palm.spring.boot.simple.ehcache.EhcacheApplication;
import org.palm.spring.boot.simple.ehcache.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EhcacheApplication.class)
public class CacheEhCacheTest {

    @Autowired
    private EhcacheService ehcacheService;

    @Test
    public void ehcacheTest() {
        //EhCache缓存SpringBoot
        String setCacheValue=ehcacheService.setCacheValue("001");
        String getCacheValue=ehcacheService.getCacheValue("001");

        System.out.println("设置缓存："+setCacheValue+"，获取缓存："+getCacheValue);
    }

}
