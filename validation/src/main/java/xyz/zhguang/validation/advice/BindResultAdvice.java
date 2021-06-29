package xyz.zhguang.validation.advice;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class BindResultAdvice {


    //Validated注解写在方法上报这个异常
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String handleException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuffer sb = new StringBuffer("BindResultAdvice:");
        for (FieldError error : fieldErrors) {
            sb.append("属性：")
                    .append(error.getField())
                    .append("校验不通过，原因：")
                    .append(error.getRejectedValue());
        }
        return sb.toString();
    }

    //Validated注解写在方法上报这个异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public String handleException1(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        StringBuffer sb = new StringBuffer("BindResultAdvice:");
        for (ConstraintViolation error : constraintViolations) {
            sb.append("属性：")
                    .append(error.getPropertyPath())
                    .append("属性值：")
                    .append(error.getInvalidValue())
                    .append("校验不通过，提示：")
                    .append(error.getMessage())
                    .append("消息模板：")
                    .append(error.getMessageTemplate());
        }
        return sb.toString();
    }

}
