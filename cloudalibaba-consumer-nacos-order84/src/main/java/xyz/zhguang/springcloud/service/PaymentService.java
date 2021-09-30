package xyz.zhguang.springcloud.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;

@FeignClient(value = "nacos-payment-provider",fallback = PaymentFallbackService.class)
public interface PaymentService {
    @GetMapping("/paymentSQL/{id}") //和9003的controller对应
    CommonResult<Payment> paymentSQL(@PathVariable("id")Long id);
}
