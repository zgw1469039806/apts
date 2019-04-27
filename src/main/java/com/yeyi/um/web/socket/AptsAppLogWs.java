package com.yeyi.um.web.socket;

import com.yeyi.um.entity.AptsApp;
import com.yeyi.um.service.impl.AptsAppServiceImpl;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @description:
 * @author: 张国伟
 * @date: 2019-01-02 16:25
 */

@ServerEndpoint("/AptsAppLogWs/{appId}")
@Component
public class AptsAppLogWs {

    private static AptsAppServiceImpl aptsAppService;

    public static void setAptsAppService(AptsAppServiceImpl aptsAppService) {
        AptsAppLogWs.aptsAppService = aptsAppService;
    }

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(@PathParam("appId") String appId, Session session) {
        try {
            AptsApp aptsApp = aptsAppService.selectById(appId);
            String logUrl = aptsApp.getFileUrl() + "/logs/spring.log";
            process = Runtime.getRuntime().exec("tail -f " + logUrl);
            inputStream = process.getInputStream();
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (process != null) {
            process.destroy();
        }
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }

    public class TailLogThread extends Thread {

        private BufferedReader reader;
        private Session session;

        public TailLogThread(InputStream in, Session session) {
            this.reader = new BufferedReader(new InputStreamReader(in));
            this.session = session;

        }

        @Override
        public void run() {
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    session.getBasicRemote().sendText(line + "<br>");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
