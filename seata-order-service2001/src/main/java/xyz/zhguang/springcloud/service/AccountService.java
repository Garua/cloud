package xyz.zhguang.springcloud.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.zhguang.springcloud.domain.CommonResult;

import java.math.BigDecimal;
@FeignClient(value = "seata-account-service")
@Component
public interface AccountService {

    @PostMapping ("/account/decrease")
    CommonResult decrease(@RequestParam("userId")Long userId, @RequestParam("money")BigDecimal money);
}
