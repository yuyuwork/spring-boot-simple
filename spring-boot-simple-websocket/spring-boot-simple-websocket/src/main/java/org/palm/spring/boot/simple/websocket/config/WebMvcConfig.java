package org.palm.spring.boot.simple.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvc配置类
 *
 * @author
 * @date 2018/12/11 10:30
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //广播通知视图
        registry.addViewController("/ws").setViewName("/websocket/broadcastWs");
        //通知视图配置
        registry.addViewController("/noticeWs").setViewName("/websocket/noticeWs");
    }

}
