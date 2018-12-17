package org.palm.spring.boot.simple.mq.websocket.interceptor.websocket;

import org.palm.spring.boot.simple.mq.websocket.common.Constants;
import org.palm.spring.boot.simple.mq.websocket.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import javax.annotation.Resource;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 自定义{@link org.springframework.web.socket.server.support.DefaultHandshakeHandler}，实现“生成自定义的{@link Principal}”
 *
 * @author
 * @date 2018/10/11
 * @since 1.0.0
 */
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //HttpSession session = SpringContextUtils.getSession();
        //User loginUser = (User) session.getAttribute(Constants.SESSION_USER);

        //loginUser != null
        String username="张三";
        if(1==1){
            logger.debug(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", username));
            //1. 将用户名存到Redis中
            redisService.addToSet(Constants.REDIS_WEBSOCKET_USER_SET, username);

            //2. 返回自定义的Principal
            return new MyPrincipal(username);
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }
    }

}
