package xyz.zhguang.validation.handler;


import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhguang.validation.ValidationUtil;
import xyz.zhguang.validation.bean.UserInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

@RestController
//表示整个类都启用校验
@Validated
public class UserInfoHandler {
    @GetMapping("/getByName")
    public String getByName(@NotNull String name) {

        return name + "ok";
    }

    //编程式校验
    @GetMapping("/addUser")
    public String addUser(UserInfo userInfo) {
        List<String> result = ValidationUtil.valid(userInfo);
        if (result.size() > 0) {
            System.out.println(result);
            return "校验不成功";
        }
        return "ok";
    }

    //声明式校验 Valid不支持分组校验
    @GetMapping("/addUser2")
    public String addUser2(@Valid UserInfo userInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {//判断是否有不满足约束的
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                System.out.println(error.getObjectName() + "::" + error.getDefaultMessage());
            }
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + "::" + fieldError.getDefaultMessage() + "::"
                        + fieldError.getRejectedValue());
            }
        }
        return "ok";
    }

    /**
     * 测试Validated分组校验
     *
     * @param userInfo
     * @param bindingResult
     * @return
     */
    //声明式校验 Valid不支持分组校验
    @GetMapping("/addUser3")
    public String addUser3(@Validated(value = {UserInfo.Add.class, Default.class}) UserInfo userInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {//判断是否有不满足约束的
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                System.out.println(error.getObjectName() + "::" + error.getDefaultMessage());
            }
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println(fieldError.getField() + "::" + fieldError.getDefaultMessage() + "::"
                        + fieldError.getRejectedValue());
            }
        }
        return "ok";
    }

    @GetMapping("/addUser4")
    public String addUser4(@Validated(value = {UserInfo.Add.class, Default.class}) UserInfo userInfo) {

        return "add4添加成功！";
    }

    /**
     * 在每个controller中写上@ExceptionHandler
     * 可以处理当前cont
     * @param e
     * @return
     */
    //@ExceptionHandler(BindException.class)
    //public String handleException(BindException e) {
    //    List<FieldError> fieldErrors = e.getFieldErrors();
    //    StringBuffer sb = new StringBuffer();
    //    for (FieldError error : fieldErrors) {
    //        sb.append("属性：")
    //                .append(error.getField())
    //                .append("校验不通过，原因：")
    //                .append(error.getRejectedValue());
    //    }
    //    return sb.toString();
    //}

}
