package com.yeyi.um.web.socket;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint("/aptsAppWs/{appId}")
@Component
public class AptsAppWs {
    protected Logger log = LoggerFactory.getLogger(getClass());

    private Session session;

    public static ConcurrentMap<Integer, AptsAppWs> concurrentMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("appId") Integer appId, Session session) {
        log.info("有新的连接加入，appId：{}", appId);
        this.session = session;
        concurrentMap.put(appId, this);
    }

    @OnClose
    public void onClose(@PathParam("appId") Integer appId) {
        concurrentMap.remove(appId);
        log.info("有连接退出，appId：{},剩下数量为：", appId, concurrentMap.size());
    }

    public void sendMessage(String msg) {
        session.getAsyncRemote().sendText(msg);
    }

    public void sendMessage(Object msg) {
        String jsonString = JSON.toJSONString(msg);
        session.getAsyncRemote().sendText(jsonString);
    }
}
