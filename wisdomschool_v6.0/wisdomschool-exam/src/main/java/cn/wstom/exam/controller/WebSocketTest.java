package cn.wstom.exam.controller;



import cn.wstom.exam.entity.Websocket;
import cn.wstom.exam.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Component
@ServerEndpoint(value = "/wsk")
public class WebSocketTest extends BaseController {
    @Autowired
    private WebsocketService webSocketService;

    private Websocket websocket;

    private static WebSocketTest websockettest;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String sessionId;

    private String testId;

    private String name;

    @PostConstruct
    public void init() {
        websockettest = this;
        websockettest.webSocketService = this.webSocketService;
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("close:" + this.session.getId());
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, IllegalAccessException, Exception {
        System.out.println("心跳正常……" + message);
        sessionId = session.getId();
        String[] strArr = message.split("\\:");
//       testId = strArr[1];
//        name = strArr[0];
//        test(name, sessionId, testId);
        // System.out.println("来自客户端" + session.getId() + "的消息:" + message);
        //String sendNessage = message;
        // sendMessage(sendNessage);

        //群发消息
        for (WebSocketTest item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException, IllegalAccessException, Exception {
        String studentName = message;
        synchronized(session){
            this.session.getBasicRemote().sendText(message);
        }
    }

    public void test(String name, String sessionId, String testId) {

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }

    public WebsocketService getWebSocketService() {
        return webSocketService;
    }

    public void setWebSocketService(WebsocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public Websocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(Websocket websocket) {
        this.websocket = websocket;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketTest.onlineCount = onlineCount;
    }

    public static CopyOnWriteArraySet<WebSocketTest> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(CopyOnWriteArraySet<WebSocketTest> webSocketSet) {
        WebSocketTest.webSocketSet = webSocketSet;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static WebSocketTest getWebsockettest() {
        return websockettest;
    }

    public static void setWebsockettest(WebSocketTest websockettest) {
        WebSocketTest.websockettest = websockettest;
    }
}
