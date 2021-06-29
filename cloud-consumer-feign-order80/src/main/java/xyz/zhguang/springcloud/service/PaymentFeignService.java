package xyz.zhguang.springcloud.service;


import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;

import java.util.concurrent.TimeUnit;

@Component
//指定访问微服务的名称
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    //和服务提供者的对应
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeOut();
}
