package com.cloud.example.domain;


import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * @author ; lidongdong
 * @Description validator校验框架验证组
 * @Date 2019/1/15
 */
@GroupSequence({ValidateGroup.GroupA.class, ValidateGroup.GroupB.class, ValidateGroup.GroupC.class, Default.class})
public class ValidateGroup {

    public interface GroupA {
    }

    public interface GroupB {
    }

    public interface GroupC {
    }
}
