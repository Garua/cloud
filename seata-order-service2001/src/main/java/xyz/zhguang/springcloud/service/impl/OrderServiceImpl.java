package xyz.zhguang.springcloud.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.zhguang.springcloud.dao.OrderDao;
import xyz.zhguang.springcloud.domain.Order;
import xyz.zhguang.springcloud.service.AccountService;
import xyz.zhguang.springcloud.service.OrderService;
import xyz.zhguang.springcloud.service.StorageService;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    @GlobalTransactional(name = "test",rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        //创建订单
        log.info("----->开始新建订单");
        orderDao.create(order);

        //扣减库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减End");

        //扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减End");

        //修改订单状态
        log.info("----->开始修改订单状态");
        orderDao.update(order.getUserId(), 0);
        log.info("----->修改订单状态结束");

        log.info("----->下单结束了，哈哈^_^");
    }
}
