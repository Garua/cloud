package xyz.zhguang.springcloud.service;


import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountService {
    void decrease(Long userId,BigDecimal money);

}
