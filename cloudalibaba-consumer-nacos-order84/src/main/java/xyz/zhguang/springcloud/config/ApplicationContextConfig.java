package xyz.zhguang.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced//负载均衡调用，不加报错
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
