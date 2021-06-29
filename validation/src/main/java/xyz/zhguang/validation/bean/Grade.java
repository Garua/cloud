package xyz.zhguang.validation.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
//开启链式调用
@Accessors(chain = true)
public class Grade {
    @NotBlank
    private String no;
}
