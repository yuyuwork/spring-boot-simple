package org.palm.spring.boot.simple.websocket.domain.dto;

/**
 * 服务器向浏览器传送消息，用该类进行传送
 *
 * @author
 * @date 2018/12/11 10:25
 */
public class WiselyResponseDTO {

    private String responseMessage;

    public WiselyResponseDTO(String responseMessage){
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage(){
        return responseMessage;
    }

}
