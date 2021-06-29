package xyz.zhguang.validation.bean;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import xyz.zhguang.validation.anno.UserStatus;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfo {
    /**
     * 校验的时候不传组名,默认 {@link javax.validation.groups.Default}
     */
    //新增组
    public interface Add{}
    //更新组
    public interface Update{}

    @Null(groups = {Add.class}) //只适用于新增
    @NotNull(groups = {Update.class}) //只适用于修改
    private Long id;
    /**
     * name ：不能是null,"","    "
     */
    //@NotNull//只校验是否为null,空串不校验
    //@NotEmpty  //校验!=null && !""
    @NotBlank(message = "名字不能为空。") //校验!=null && !"" && !"   "
    private String name;
    @NotNull
    @Min(value = 18,message = "{value}以下禁止进入") @Max(800)  //闭区间 ，仅在age不为null的时候生效。为null时，跳过校验
    //@Range(min = 1,max = 800) //闭区间
    private Integer age;

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "^((13[0-9])|(14[0|5|6|7|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$")
    private String phone;

    //适用于字符串，用在这报错
    @Past //过去的时间
    private LocalDateTime birthday;
    @URL
    private String personalPage;

    @NotNull
    @Valid //级联校验，可以校验grade中的属性约束
    private Grade grade;

    /**
     * 自定义注解校验
     */
    @UserStatus
    private Integer status;

}
