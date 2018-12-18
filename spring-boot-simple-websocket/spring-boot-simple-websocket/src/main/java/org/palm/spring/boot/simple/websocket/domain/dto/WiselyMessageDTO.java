package org.palm.spring.boot.simple.websocket.domain.dto;

/**
 * 浏览器向服务器传送消息，用该类进行接收
 *
 * @author
 * @date 2018/12/11 10:25
 */
public class WiselyMessageDTO {

    private String name;

    public String getName(){
        return name;
    }

}
