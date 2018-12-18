package org.palm.spring.boot.simple.mq.websocket.config;

import org.palm.spring.boot.simple.mq.websocket.interceptor.websocket.AuthHandshakeInterceptor;
import org.palm.spring.boot.simple.mq.websocket.interceptor.websocket.MyChannelInterceptor;
import org.palm.spring.boot.simple.mq.websocket.interceptor.websocket.MyHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * WebSocket相关配置
 *
 * @author
 * @date 2018/12/13 13:47
 */
@Configuration
//注解使用STOMP协议传输基于代理消息
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private AuthHandshakeInterceptor authHandshakeInterceptor;
    @Autowired
    private MyHandshakeHandler myHandshakeHandler;
    @Autowired
    private MyChannelInterceptor myChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册STOMP协议节点，同时指定使用SockJS协议
        registry.addEndpoint("/chat-websocket").addInterceptors(authHandshakeInterceptor).setHandshakeHandler(myHandshakeHandler).withSockJS();
    }

    /**
     * 由于我们是实现推送功能，这里的消息代理是/topic
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端需要把消息发送到/message/xxx地址，应用程序开头
        registry.setApplicationDestinationPrefixes("/message");
        //启动STOMP代理中继功能，并将其代理目的地前缀设置为 "/topic"
        //服务端广播消息的路径前缀，客户端需要相应订阅/topic/yyy这个地址的消息
        registry.enableSimpleBroker("/topic");
        //给指定用户发送消息的路径前缀，默认值是/user/
        registry.setUserDestinationPrefix("/user/");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(myChannelInterceptor);
    }

}
