package xyz.zhguang.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//同步刷新配置，如果不加，git上改了后，3344能获取最新，3355不行
//加了之后，修改yml,然后发送一个post请求 curl -X POST "http://localhost:3355/actuator/refresh"
@RefreshScope
public class ConfigClientController {
    /**
     * 取github上config下面的info对应的值
     */
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
