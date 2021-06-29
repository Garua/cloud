package xyz.zhguang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
//@EnableEurekaClient 不加这个也能注册成功，
// EurekaClientAutoConfiguration这个作为eureka的自动配置类上面有
// @ConditionalOnProperty(value = "eureka.client.enabled", matchIfMissing = true)
// 这一行的意思是只有当application.yaml（或者环境变量，或者系统变量）里，
// eureka.client.enabled这个属性的值为true才会初始化这个类（缺省值也为true，但是假如你手动赋值为false，就不会初始化这个类了）。
//对应上了上面写的【基本配置】里的第一个配置。


//EurekaAutoServiceRegistration类和@Bean注解
//在EurekaClientAutoConfiguration类里，我们可以看到这样一个方法
//
//这个方法的意思是通过@Bean注解，装配一个EurekaAutoServiceRegistration对象作为Spring的bean，
// 而我们从名字就可以看出来EurekaClient的注册就是EurekaAutoServiceRegistration对象所进行操作的。
//
//同时，在这个方法上，
// 也有这么一行@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled",
// matchIfMissing = true)，我们可以看出来，
// EurekaClient的注册，原来其实是和两个配置项有关的，一个是eureka.client.enabled，
// 另一个是spring.cloud.service-registry.auto-registration.enabled，只不过这两个配置默认都是true。


public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class,args);
    }
}
