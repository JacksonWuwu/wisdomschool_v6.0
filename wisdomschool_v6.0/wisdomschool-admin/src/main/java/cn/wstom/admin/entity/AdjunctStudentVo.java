package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;

import lombok.Data;

@Data
public class AdjunctStudentVo  {
    /**
     * 上机名称
     */
    @Excel(name = "上机名称")
    private String adjName;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    private String userName;

    /**
     * 学生id
     */
    @Excel(name = "学号")
    private String loginName;

    /**
     * 提交时间
     */
    @Excel(name = "提交时间")
    private String submitline;

    /**
     * 状态
     */
    @Excel(name = "是否已经提交")
    private String States;
    /**
     * 文件名称
     */
    @Excel(name = "文件名字")
    private String filename;

    /**
     * 班级名字
     * */
    @Excel(name = "班级名字")
    private String clbumName;
}
