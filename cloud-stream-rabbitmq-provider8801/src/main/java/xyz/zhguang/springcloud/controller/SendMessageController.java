package xyz.zhguang.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.springcloud.service.IMessageProvider;

import javax.annotation.Resource;

@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;


    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }
}
