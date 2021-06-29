package xyz.zhguang.validation;

import xyz.zhguang.validation.service.UserInfoService;

public class App {
    public static void main(String[] args) {
        UserInfoService userInfoService = new UserInfoService();
        userInfoService.getByName(null);
    }
}
