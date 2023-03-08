package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteOptionSubmit extends BaseEntity {

    private String stuid;
    //  学生选项Id
    private Integer voteId;
    //  选项答案（option）多选单选
    private String stuOptionAnswer;
    //  文字答案
    private String stuAnswer;
    private String stuAnswerVo;
    private int optionSize;

    private int ststatus;
}
