package xyz.zhguang.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.service.PaymentHystrixService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService service;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return service.paymentInfo_OK(id);
    }

     @GetMapping("/consumer/payment/hystrix/timeout/{id}")
     //设置出现问题(超时或者异常)的失败兜底方法，服务降级
     //@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
     //        //3秒内是正常的，不超时
     //        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "11500")
     //})
     @HystrixCommand //不指定fallback时，走全局
     public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
         int a = 10 / 0;
         return "线程池："+Thread.currentThread().getName()+"  ,id: "+id+"\t呜呜呜呜，耗时(秒)";

     }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "我是消费者80,对方支付系统繁忙请稍后再试，或者自查";
    }

    //下面是全局fallback方法，未指定走这个
    public String payment_Global_FallbackMethod(){

        return "Global异常信息处理，请稍后再试，~~~~(>_<)~~~~";
    }



}
