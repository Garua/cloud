package xyz.zhguang.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;
import xyz.zhguang.springcloud.service.PaymentService;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    public RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
    // @SentinelResource(value = "fallback") 没有配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback")//负责业务异常的fallback
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler")//blockHandler负责sentinel控制台配置违规
    @SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback", exceptionsToIgnore = {IllegalArgumentException.class})
//两个都配置时，如果同时配置违规和业务异常，则进blockHandler
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录。空指针异常");
        }
        return result;
    }

    //fallback
    public CommonResult handlerFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "兜底异常的handlerFallback，exception内容：" + e.getMessage(), payment);
    }

    //blockHandler
    public CommonResult blockHandler(@PathVariable Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "blockHandler-sentinel限流，无此流水，blockHandlerException：" + e.getMessage(), payment);
    }

    /******************************************OpenFeign************************************/
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
