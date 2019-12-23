package com.example.client.websocket;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{userShop}")
@Component
public class WebSocketTest {


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;  //记录id
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String name;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen   //建立连接
    public void onOpen(@PathParam("userShop") String userCode,Session session) {
        System.out.println(userCode);
        this.name = userCode;
        this.session = session;     //获取session里面的连接
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose     //用户下线
    public void onClose(@PathParam("userCode") String userCode) {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage    //接收客户端发来的信息
    public void onMessage(String message) {
        if (!message.equals("err")) {
            //群发消息
            for (WebSocketTest item : webSocketSet) {
                try {
                    if (item.name.equals(message)) {
                        item.sendMessage("接收到");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    //记录id
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    // id的增加
    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    //id 的减减
    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}