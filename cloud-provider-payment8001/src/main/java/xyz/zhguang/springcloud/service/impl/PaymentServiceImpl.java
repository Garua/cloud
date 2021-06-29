package xyz.zhguang.springcloud.service.impl;

import org.springframework.stereotype.Service;
import xyz.zhguang.springcloud.dao.PaymentDao;
import xyz.zhguang.springcloud.entities.Payment;
import xyz.zhguang.springcloud.service.PaymentService;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
