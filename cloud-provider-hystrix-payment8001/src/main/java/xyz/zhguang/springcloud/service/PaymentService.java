package xyz.zhguang.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {


    //服务降级
    /**
     * 正常访问，肯定Ok
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"  paymentInfo_OK,id: "+id+"\t哈哈^_^";
    }

    //设置出现问题(超时或者异常)的失败兜底方法，服务降级
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            //3秒内是正常的，不超时
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int a = 10 / 0;
        return "线程池："+Thread.currentThread().getName()+"  ,id: "+id+"\t呜呜呜呜，耗时(秒)";

    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"  8001系统繁忙，请稍后再试,id: "+id+"\t呜呜呜呜，/(ㄒoㄒ)/~~";

    }
    //服务熔断

    // HystrixCommandProperties 属性在这个抽象类中
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //假设10秒钟之内(熔断的前提是请求次数到达阀值)，10次请求6次失败，那么断路器发挥作用
            @HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "6000"),//时间窗口器
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后熔断
    })
    public String paymentCircuitBreaker(@PathVariable("id") Long id){
        if(id < 0){
            throw new RuntimeException("**********id不能为负数**********");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() +"\t"+"调用成功，流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Long id){
        return "id不能为负数，请稍后再试，/(ㄒoㄒ)/~~  id:"+id;
    }

}
