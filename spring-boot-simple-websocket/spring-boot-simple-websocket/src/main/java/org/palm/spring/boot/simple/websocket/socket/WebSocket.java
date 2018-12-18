package org.palm.spring.boot.simple.websocket.socket;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * webSocket发布
 *
 * @author
 * @date 2018/12/11 14:08
 */
@Component
@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocket {

    /** 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的 */
    private static int onlineCount = 0;
    /** concurrent包的线程安全Set，用来存放每个客户端对应的ProductWebSocket对象 */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /** 接收sid */
    private String sid="";
    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("sid")String userId, Session session) {
        System.out.println("新客户端连入，用户id：" + userId);
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();

        System.out.println("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        this.sid=userId;

        //相关业务处理，根据拿到的用户ID判断其为那种角色，根据角色ID去查询是否有需要推送给该角色的消息，有则推送
        if(StringUtils.isNotBlank(userId)) {
            //获取需要推送的消息
            List<String> totalPushMsgs = new ArrayList<String>();
            totalPushMsgs.add("消息测试-001");
            totalPushMsgs.add("消息测试-002");

            if(totalPushMsgs != null && !totalPushMsgs.isEmpty()) {
                totalPushMsgs.forEach(e -> sendMessage(e));
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("一个客户端关闭连接");
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();

        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自窗口"+sid+"的信息:"+message);
        //群发消息
        for (WebSocket item : webSocketSet) {
            item.sendMessage(message);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("websocket出现错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            System.out.println("推送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message,@PathParam("sid") String sid) {
        System.out.println("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocket webSocket : webSocketSet) {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if(sid==null) {
                webSocket.sendMessage(message);
            }else if(webSocket.sid.equals(sid)){
                webSocket.sendMessage(message);
            }
            continue;
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

}
