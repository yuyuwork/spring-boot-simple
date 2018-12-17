package org.palm.spring.boot.simple.mq.websocket.model.websocket;

import lombok.Data;

/**
 * Greeting
 *
 * @author
 * @date 2018/9/30
 * @since 1.0.0
 */
@Data
public class HelloMessage {

    private String content;

    public HelloMessage() {}

    public HelloMessage(String content) {
        this.content = content;
    }

}
