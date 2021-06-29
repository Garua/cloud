package xyz.zhguang.validation;

import xyz.zhguang.validation.bean.Grade;
import xyz.zhguang.validation.bean.UserInfo;

import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.List;

public class ValidationTest {
    public static void main(String[] args) {
        UserInfo user = new UserInfo();
        user.setName("loocc");
        user.setAge(1);
        user.setEmail("234823423@qq.com");
        user.setPhone("18881202343");
        user.setPersonalPage("https://www.zhguang.xyz");
        user.setBirthday(LocalDateTime.now().minusDays(1));
        user.setGrade(new Grade());
        user.setStatus(1004);
        //新增组
        //List<String> valid = ValidationUtil.valid(user, UserInfo.Add.class, Default.class);
        List<String> valid = ValidationUtil.validFailFast(user, UserInfo.Add.class, Default.class);
        System.out.println(valid);
        //更新组，校验不通过
        //List<String> valid1 = ValidationUtil.valid(user, UserInfo.Update.class, Default.class);
        //System.out.println(valid1);

    }
}
