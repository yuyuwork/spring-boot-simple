package org.palm.spring.boot.simple.websocket.controller;

import org.palm.spring.boot.simple.websocket.domain.dto.WiselyMessageDTO;
import org.palm.spring.boot.simple.websocket.service.SimpleService;
import org.palm.spring.boot.simple.websocket.socket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WsController {

    @Autowired
    public SimpMessagingTemplate template;
    @Autowired
    private SimpleService simpleService;

    @MessageMapping("/welcome")
    //@SendTo("/topic/getResponse")
    public String welcome(WiselyMessageDTO message) {
        simpleService.welcome(message);
        return "";
    }

    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid, String message) {
        WebSocket.sendInfo(message,cid);
        return cid;
    }

}
