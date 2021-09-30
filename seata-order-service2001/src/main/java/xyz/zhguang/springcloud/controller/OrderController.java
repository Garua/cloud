package xyz.zhguang.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.domain.CommonResult;
import xyz.zhguang.springcloud.domain.Order;
import xyz.zhguang.springcloud.service.OrderService;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order) {
        System.out.println(order);
        orderService.create(order);
        return new CommonResult(200,"创建订单成功！");
    }
}
