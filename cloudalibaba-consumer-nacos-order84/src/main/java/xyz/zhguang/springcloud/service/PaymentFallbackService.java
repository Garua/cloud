package xyz.zhguang.springcloud.service;

import org.springframework.stereotype.Component;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444,"服务降级返回，PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
