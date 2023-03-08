package cn.wstom.student.entity;


import cn.wstom.student.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 用于登陆、验证、记录等作用，
 * 具体业务逻辑用户需继承该用户对象添加业务相关的字段
 *
 * @author dws
 */
@Getter
@Setter
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 8250276702860908568L;

    /**
     * 登录名称
     * 学生用户为学号
     * 教师用户为教职工号
     */
    @Excel(name = "学号/教职工号")
    private String loginName;

    /**
     * 用户名称
     */
    @Excel(name = "姓名")
    private String userName;

    /**
     * 用户属性类型
     * 0：学生用户
     * 1：教师用户
     * 2：教务员用户
     */
    private Integer userType;

    /**
     * 用户属性id
     * 学生用户 &lt;---&gt; 学生属性id
     * 教师用户 &lt;---&gt; 教师属性id
     * 教务员用户 &lt;---&gt; 教师属性id
     */
    private String userAttrId;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phoneNumber;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDate;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知", combo = {"男", "女", "未知"})
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 盐加密
     */
    @JSONField(serialize = false)
    private String salt;

    /**
     * 身份证号
     */
    private String idCardNum;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 曾用名
     */
    private String formerName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 户口类型
     */
    private String accountType;

    /**
     * 户口所在地
     */
    private String accountAddress;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 附件地址
     */
    private String attachmentAddr;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 民族
     */
    private String nation;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", type = Excel.Type.EXPORT)
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    private List<SysRole> roles;

    private Integer posts;

    private String signature;

    /**
     * 答复数
     */
    private Integer answer;

    /**
     * 话题数
     */
    private Integer topic;

    /**
     * 问题数
     */
    private Integer question;

    /**
     * 关注问题数
     */
    private Integer questionFollow;

    /**
     * 分享数
     */
    private Integer share;

    /**
     * 角色组
     */
    private String[] roleIds;

    /**
     * 评论数
     */
    private Integer comment;

    private Integer schoolId;

    /**
     * 积分数
     */
    private Integer credit;
    private String name;
    private int rid;



    public SysUser(){

    }

    public SysUser(Integer userType,String userAttrId){
        this.userType = userType;
        this.userAttrId = userAttrId;
    }
    public String getUserAttrId() {
        return userAttrId;
    }

    public void setUserAttrId(String userAttrId) {
        this.userAttrId = userAttrId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("loginName", loginName)
                .append("userName", userName)
                .append("userType", userType)
                .append("userAttrId", userAttrId)
                .append("email", email)
                .append("phoneNumber", phoneNumber)
                .append("birthDate", birthDate)
                .append("sex", sex)
                .append("avatar", avatar)
                .append("password", password)
                .append("salt", salt)
                .append("idCardNum", idCardNum)
                .append("nativePlace", nativePlace)
                .append("political", political)
                .append("homeAddress", homeAddress)
                .append("formerName", formerName)
                .append("age", age)
                .append("accountType", accountType)
                .append("accountAddress", accountAddress)
                .append("mobilePhone", mobilePhone)
                .append("attachmentAddr", attachmentAddr)
                .append("attachmentName", attachmentName)
                .append("nation", nation)
                .append("status", status)
                .append("delFlag", delFlag)
                .append("loginIp", loginIp)
                .append("loginDate", loginDate)
                .append("roles", roles)
                .append("roleIds", roleIds)
                .append("id", id)
                .append("createBy", createBy)
                .append("createTime", createTime)
                .append("updateBy", updateBy)
                .append("posts", posts)
                .append("signature", signature)
                .append("updateTime", updateTime)
                .append("remark", remark)
                .append("version", version)
                .toString();
    }
}
