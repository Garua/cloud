package xyz.zhguang.springcloud.myhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import xyz.zhguang.springcloud.entities.CommonResult;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException e){
        return new CommonResult(444,"按客户自定义，global handlerException--------1");
    }

    public static CommonResult handlerException2(BlockException e){
        return new CommonResult(444,"按客户自定义，global handlerException-----------2");
    }
}
