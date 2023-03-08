package cn.wstom.face.entity;



import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试关联表 tk_user_exam
 *
 * @author lzj
 * @date 20200608
 */
@Data
public class UserExam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建Id
     */
    private String createId;
    /**
     * 更新人Id
     */
    private String updateId;
    /**
     * 学校No
     */
    private String schoolNo;
    /**
     * 学生开始时间
     */
    private String stuStartTime;
    /**
     * 学生结束时间
     */
    private String stuEndTime;
    /**
     * 试卷总分
     */
    private String paperscore;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 试卷Id
     */
    private String testPaperOneId;
    /**
     * 提交状态 0 没有 1是
     */
    private String sumbitState;
    /**
     * 学生确定提交状态 0 没有 1是
     */
    private String sumScore;
    private String beiZhu;  //备注
    private String cId; //课程id
    private String tcId; //班级id

    private String tcName; //班级名称
    private String tgId; //年级

    private String tgName; //年级名称
    private String tscId; //学生绑定的课程id
    private String tsId; //学生id

    private String sysName; //学生名称
    private String suId; //用户id
    private String tcoId; //班级所绑定的课程
    private String tcoName; //班级所绑定的课程名称

    private String testName;//试卷名称
    private String type;//试卷类型
    /**
     * 学生学号
     */

    private String stuNum;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 试卷标题
     */
    private String headLine;
    /**
     * 是否已改卷
     */
    private String setScored;

    /**
     * 测试时间
     */
    private String testTime;
    /**
     * 改卷状态 0：未 ，1：是
     */
    private String madeScore;

    /**
     * 作答时间
     */
    private long AnswerTime;
    /**
     * 学生成绩
     */

    private String stuScore;
    /**
     * 分数数组
     */
    private ArrayList  score = new ArrayList();
    /**
     * 区域数组
     */
    private ArrayList spaceName = new ArrayList();
    /**
     * 是否开考标记
     */
    private String setExam;
    /**
     * 考试时长
     */
    private String time;
    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * session创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTimestamp;
    /**
     * 登录地址
     */
    private String loginLocation;
    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;
    private String status;
    /**
     * 访问时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    private String msg;

    private String rule;

    private String percentage;


}
