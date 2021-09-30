package xyz.zhguang.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.entities.CommonResult;
import xyz.zhguang.springcloud.entities.Payment;
import xyz.zhguang.springcloud.myhandler.CustomerBlockHandler;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value="byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试OK",new Payment(2021L,"serial001"));
    }
    public CommonResult handleException(BlockException e){
        return new CommonResult(444,e.getClass().getCanonicalName()+"\t服务不可用！");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value="byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按URL限流测试OK",new Payment(2021L,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value="customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException2")//指定哪个类的哪个方法兜底处理
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"按客户自定义OK",new Payment(2021L,"serial003"));
    }
}
