package xyz.zhguang.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.domain.CommonResult;
import xyz.zhguang.springcloud.service.StorageService;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    @PostMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count){

        storageService.decrease(productId,count);
        return new CommonResult(200,"扣减库存成功！");
    }
}
