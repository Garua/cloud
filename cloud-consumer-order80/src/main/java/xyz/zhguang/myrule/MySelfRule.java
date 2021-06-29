package xyz.zhguang.myrule;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ribbon是一个客户端的负载均衡，这里配置负载算法为随机
 * ，同时再主启动类上加上@RibbonClient()
 *
 * 此类不能放在{@link org.springframework.context.annotation.ComponentScan}该注解不扫描的位置
 */


@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
