package xyz.zhguang.validation.anno;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * ConstraintValidator<UserStatus,Integer> ,UserStatus表示校验UserStatus这个注解，Integer表示该注解适用integer字段上
 */
public class UserStatusValidator implements ConstraintValidator<UserStatus, Integer> {
    @Override
    public void initialize(UserStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //为null时跳过校验规则
        if (value == null) {
            return true;
        }
        Set<Integer> set = new HashSet<>(3);
        set.add(1000);
        set.add(1001);
        set.add(1002);
        return set.contains(value);
    }
}
