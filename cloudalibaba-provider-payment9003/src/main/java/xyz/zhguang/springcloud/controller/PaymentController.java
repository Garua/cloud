package xyz.zhguang.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;

import java.util.HashMap;

@RestController
public class PaymentController {


    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"11111111111111111"));
        hashMap.put(2L,new Payment(2L,"22222222222222222"));
        hashMap.put(3L,new Payment(3L,"33333333333333333"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult<>(200,"from mysql,serverPort: "+serverPort,payment);
        return result;
    }
}
