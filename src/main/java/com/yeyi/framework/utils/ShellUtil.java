package com.yeyi.framework.utils;

import com.alibaba.fastjson.JSON;
import com.yeyi.um.entity.AptsApp;
import com.yeyi.um.service.impl.AptsAppServiceImpl;
import com.yeyi.um.web.socket.AptsAppWs;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @description:
 * @author: 张国伟
 * @date: 2018-12-06 10:02
 */
@Component
public class ShellUtil {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Value("${apts.aptsFile.jarFiles}")
    private String jarFiles;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private AptsAppServiceImpl aptsAppService;

    /**
     * 创建应用所在的文件夹
     */
    public void createDirectory(String fileUrl) {
        File file = new File(fileUrl);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public String getFileStatus(String fileName) {
        String result = null;
        try {
            Process process = Runtime.getRuntime().exec("pgrep -f " + fileName);
            InputStream inputStream = process.getInputStream();
            byte[] buf = new byte[1024];
            int i = inputStream.read(buf);
            inputStream.close();
            if (i == -1) {
                return result;
            }
            result = new String(buf, 0, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param fileName jar包的名称
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public boolean startFile(Integer appId,String fileName, String fileUrl){
        String logUrl = fileUrl + "/logs/";
        File file = new File(logUrl);
        if (!file.exists()) {
            file.mkdir();
        }
        String pid = getFileStatus(fileName);
        //如果正在运行返回假
        if (StringUtils.isNotEmpty(pid)) {
            return false;
        }
        try {
            String[] cmds = {"/bin/sh", "-c", "java -jar " + jarFiles + fileName + " >> " + logUrl + "spring.log 2>&1 &"};
            Process exec = Runtime.getRuntime().exec(cmds);
            //设置线程超时间为50秒
            Integer timeOut = 50 * 1000;
            Thread thread = new Thread(() -> {
                try {
                    //线程启动时间
                    long startTime = System.currentTimeMillis();
                    Process process = Runtime.getRuntime().exec("tail -f -n 0 " + logUrl + "spring.log");
                    InputStream inputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    Map<String, Integer> resultMap = new HashMap<>();
                    String line;
                    while((line = reader.readLine()) != null) {
                        if (line.indexOf("Started") > -1) {
                            resultMap.put("code", 200);
                            break;
                        } else if (line.indexOf("ERROR") > -1) {
                            resultMap.put("code", 400);
                            break;
                        }
                    }
                    AptsAppWs aptsAppWs = AptsAppWs.concurrentMap.get(appId);
                    if (aptsAppWs != null) {
                        aptsAppWs.sendMessage(resultMap);
                    }
                    reader.close();
                    inputStream.close();
                    AptsApp aptsApp = new AptsApp();
                    aptsApp.setId(appId);
                    aptsApp.setState(2);
                    aptsAppService.updateById(aptsApp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
//            thread.interrupt();

//            thread.interrupt();
            executorService.submit(thread);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean stopFile(String fileName) {
        String pid = getFileStatus(fileName);
        if (StringUtils.isEmpty(pid)) {
            return false;
        }
        try {
            Runtime.getRuntime().exec("kill -9 " + pid);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
