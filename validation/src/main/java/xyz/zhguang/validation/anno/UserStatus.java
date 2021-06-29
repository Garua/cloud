package xyz.zhguang.validation.anno;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
/**
 * 此类{@link UserStatusValidator} 要实现 {@link javax.validation.ConstraintValidator}
 * Accepts {@code Integer}
 */
@Constraint(validatedBy = { UserStatusValidator.class})//说明当前注解要被谁来完成校验工作
@Target({ FIELD })
@Retention(RUNTIME)
public @interface UserStatus {
    String message() default "status必须是1000/1001/1002";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
