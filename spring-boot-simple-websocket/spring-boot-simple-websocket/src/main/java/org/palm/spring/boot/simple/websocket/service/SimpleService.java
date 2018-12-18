package org.palm.spring.boot.simple.websocket.service;

import org.palm.spring.boot.simple.websocket.domain.dto.WiselyMessageDTO;
import org.palm.spring.boot.simple.websocket.domain.dto.WiselyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 服务层例子类
 *
 * @author
 * @date 2018/12/11 11:25
 */
@Service
public class SimpleService {

    @Autowired
    public SimpMessagingTemplate template;

    public void welcome(WiselyMessageDTO message){
        //广播使用convertAndSend方法，第一个参数为目的地，和js中订阅的目的地要一致
        template.convertAndSend("/topic/getResponse", new WiselyResponseDTO("Welcome, " + message.getName() + "!"));
    }

}
