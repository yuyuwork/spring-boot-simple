package org.palm.spring.boot.simple.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mvc启动类
 *
 * @author
 * @date 2018/12/17 11:58
 */
@SpringBootApplication
public class MvcApplication {

    public static void main(String[] args) {
        //Spring Boot建议将我们main方法所在的这个主要的配置类配置在根包名下
        SpringApplication.run(MvcApplication.class,args);
    }

}
