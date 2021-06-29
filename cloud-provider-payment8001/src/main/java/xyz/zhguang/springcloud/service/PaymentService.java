package xyz.zhguang.springcloud.service;

import org.apache.ibatis.annotations.Param;
import xyz.zhguang.springcloud.entities.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
