package xyz.zhguang.validation.service;

import xyz.zhguang.validation.ValidationUtil;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.List;

public class UserInfoService {
    /**
     * 方法非bean类型的入参
     * 1.方法参数前加注解
     * 2.执行校验
     * @param name
     * @return
     */
    public String getByName(@NotNull String name) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[1];
        System.out.println(stackTraceElement);
        String mn = stackTraceElement.getMethodName();
        Method m = null;
        try {
             m = this.getClass().getDeclaredMethod(mn, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<String> strings = ValidationUtil.validNotBean(this, m, new Object[]{name});
        System.out.println(strings);
        return "ok";
    }
}
