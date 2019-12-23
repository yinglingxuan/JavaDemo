package com.example.business.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
@Component
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;  //记录id
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static Map<String,Object> map=new HashMap<>();


/*
* 商家管理页面：var ws = new WebSocket("ws://192.168.2.131:8899/websocket?shopUuid=123"); 通过该链接访问
* 用户：支付成功时，前台访问var ws = new WebSocket("ws://192.168.2.131:8899/websocket");然后发送该店铺的uuid、
* 商家后台管理：商家进入的时候，后台接受到参数shopUuid,后台map存放key：shopUuid，value存放session.getId();当有用户支付成功时，接受用户发送的uuid与map进行判断，再根据session.getId()发送到改商户
* */
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void open(Session session) {
        System.out.println(session+"商家 1111登录"+session);
        String str = session.getQueryString();
        this.session = session;
        if (str!=null){
            String shopUuid=str.substring(str.indexOf("=")+1);
            System.out.println(shopUuid+"session.getQueryString()"+str);
              //获取session里面的连接
            map.put(shopUuid,session.getId());
            map.forEach((key, value) -> {
                System.out.println("Key : " + key + " Value : " + value);
            });
        }
        webSocketSet.add(this);     //加入set中
        System.err.println("webSocketSet"+webSocketSet);
        addOnlineCount();           //在线数加1
        System.out.println("當前有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法  http%3A%2F%2Fwww.fang.com.ngrok.xiaomiqiu.cn%2Fuser%2Flogin
     */
    @OnClose     //用户下线
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage    //接收客户端发来的信息
    public void onMessage(String message, Session session) {
        if(!message.equals("err")) {
            System.out.println("来111自"+session.getId()+"的消息:" + message);
        	  //私发消息
            map.forEach((String key, Object object) -> {
//                String content="";
               if (key!=null&&message!=null&&key.equals(message)){
                   System.out.println("来2222自"+session.getId()+"的消息:" + message);
                   //                       sendMessage("@"+key+"\t\t"+message+"\t\tobject"+object);
                   for(WebSocketTest item: webSocketSet){
                       if(item.session.getId().equals(object)&&object!=null){
                           item.sendMessage("来自"+session.getId()+"的消息:" +message);
                       }
                  }
               }
            });
//        	for(WebSocketTest item: webSocketSet){
//                try {
//                    item.sendMessage("来自"+session.getId()+"的消息:" +message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
        }
    }


    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
   /* @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }*/


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) {
//        this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendText(message);
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