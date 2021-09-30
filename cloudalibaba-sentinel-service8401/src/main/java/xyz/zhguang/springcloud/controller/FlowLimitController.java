package xyz.zhguang.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FlowLimitController {


    @GetMapping("/testA")
    public String testA() {
        return "------------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "------------testB";
    }


    @GetMapping("/testD")
    public String testD() {
        log.info("testD=异常比例：");
        int age = 10 / 0;
        return "------------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE异常数");
        int age = 10 / 0;
        return "------------testE";
    }

    @GetMapping("/testHotKey")
    //处理是sentinel控制台配置违规的情况，有blockHandler兜底
    //而下面的int a = 10 /0;runtimeException,SentinelResource不管
    @SentinelResource(value = "testHotKey",blockHandler = "deal_hotkey")//指定方法兜底
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        int a = 10 / 0;
        return "------------testHotKey";
    }
    public String deal_hotkey(String p1, String p2, BlockException e){

        return "------------deal_hotkey，~~~~(>_<)~~~~";

    }
}
