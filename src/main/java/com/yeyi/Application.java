package com.yeyi;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yeyi.um.service.impl.AptsAppServiceImpl;
import com.yeyi.um.web.socket.AptsAppLogWs;
import com.yeyi.um.web.socket.AptsAppWs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableSwagger2
@MapperScan("com.yeyi.um.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(Application.class, args);
        AptsAppServiceImpl aptsAppService = ac.getBean(AptsAppServiceImpl.class);
        AptsAppLogWs.setAptsAppService(aptsAppService);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(30);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }
}
