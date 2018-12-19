package org.palm.spring.boot.simple.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * ehcache启动类
 *
 * @author
 * @date 2018/12/19 11:40
 */
@EnableCaching
@SpringBootApplication
public class EhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhcacheApplication.class,args);
    }

}
