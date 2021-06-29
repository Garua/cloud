package xyz.zhguang.validation;

import org.hibernate.validator.HibernateValidator;
import xyz.zhguang.validation.bean.UserInfo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {
    //默认把所有校验字段，遍历校验，返回所有的校验失败信息
    private static Validator validator;
    //快速失败，当遇到第一个校验不通过的字段时，结束校验，并返回失败信息
    private static Validator failFastValidator;

    //校验非bean参数
    private static ExecutableValidator executableValidator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        failFastValidator = Validation.byProvider(HibernateValidator.class)//提供都变成HibernateValidator
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
        executableValidator = validator.forExecutables();
    }

    public static <T> List<String> validNotBean(T object, Method method
    ,Object[] parameterValues,Class<?>...groups){
        Set<ConstraintViolation<T>> set = executableValidator.validateParameters(object, method, parameterValues, groups);
        List<String> collect = set.stream().map(v -> "属性：" + v.getPropertyPath() + ",属性值"
                + v.getInvalidValue() + ",校验不通过的提示信息：" + v.getMessage()
                +"，消息模板："+v.getMessageTemplate()).collect(Collectors.toList());
        return collect;

    }

    public static List<String> valid(UserInfo info,Class<?>...groups) {
        //如果校验对象没有校验通过，则set中有对应的校验信息
        return getStrings(info, validator, groups);
    }

    public static List<String> validFailFast(UserInfo info,Class<?>...groups) {
        //如果校验对象没有校验通过，则set中有对应的校验信息
        return getStrings(info, failFastValidator, groups);
    }

    private static List<String> getStrings(UserInfo info, Validator failFastValidator, Class<?>[] groups) {
        Set<ConstraintViolation<UserInfo>> set = failFastValidator.validate(info,groups);
        List<String> collect = set.stream().map(v -> "属性：" + v.getPropertyPath() + ",属性值"
                + v.getInvalidValue() + ",校验不通过的提示信息：" + v.getMessage()
                +"，消息模板："+v.getMessageTemplate()).collect(Collectors.toList());
        return collect;
    }
}
