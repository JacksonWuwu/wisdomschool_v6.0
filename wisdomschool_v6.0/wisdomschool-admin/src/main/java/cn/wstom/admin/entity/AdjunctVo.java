package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;
import lombok.Data;

@Data
public class AdjunctVo {
    /**
     * 作业名称
     */
    @Excel(name = "作业名称")
    private String adjunctname;
    /**
     * 作业内容
     */
    @Excel(name = "作业内容")
    private String jobcontent;
    /**
     * 文件名称
     */
    @Excel(name = "作业文件名称")
    private String filename;
    /**
     * 学生id
     */
    @Excel(name = "学生id")
    private String stuid;


    /*
     * 班级
     * */
    @Excel(name = "班级")
    private String name;
    @Excel(name = "学号")
    private String loginName;
    @Excel(name = "姓名")
    private String userName;
    @Excel(name = "成绩")
    private String results;

}
