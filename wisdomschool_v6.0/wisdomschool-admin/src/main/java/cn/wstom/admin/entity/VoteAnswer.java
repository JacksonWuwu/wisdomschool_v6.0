package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import cn.wstom.common.annotation.Excel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteAnswer extends BaseEntity {


    /*
    题目
    * */
    private String stoption;

    /**
     * 答案
     */
    @Excel(name = "答案")
    private String stanswer;
    /**
     * 学校No
     */
    private String  schoolNo;

    /**
     *修改者Id
     */
    String updateId;
    /**
     *添加者ID
     */
    String createId;
    /**
     * 修改人名
     */
    String updateBy;
    /**
     * 创建人名
     */
    String createBy;
}
