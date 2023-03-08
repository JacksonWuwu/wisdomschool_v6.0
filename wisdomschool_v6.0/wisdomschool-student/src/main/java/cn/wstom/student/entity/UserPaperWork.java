package cn.wstom.student.entity;


import cn.wstom.student.annotation.Excel;
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
public class UserPaperWork extends BaseEntity {
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

    private String startTime;

    private String user_state;

    private String set_Exam;

    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSet_Exam() {
        return set_Exam;
    }

    public void setSet_Exam(String set_Exam) {
        this.set_Exam = set_Exam;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 学生结束时间
     */
    private String stuEndTime;

    public String getStuStartTime() {
        return stuStartTime;
    }

    public void setStuStartTime(String stuStartTime) {
        this.stuStartTime = stuStartTime;
    }


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
    private String testPaperWorkId;
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
    @Excel(name = "班级")
    private String tcName; //班级名称
    private String tgId; //年级
    @Excel(name = "年级")
    private String tgName; //年级名称
    private String tscId; //学生绑定的课程id
    private String tsId; //学生id
    @Excel(name = "姓名")
    private String sysName; //学生名称
    private String suId; //用户id
    private String tcoId; //班级所绑定的课程
    private String tcoName; //班级所绑定的课程名称
    @Excel(name = "试卷名称")
    private String testName;//试卷名称
    private String type;//试卷类型
    /**
     * 学生学号
     */
    @Excel(name = "学号")
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
    @Excel(name = "学生成绩")
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

//    public Date getStuStartTime() {
//        return stuStartTime;
//    }
//
//    public void setStuStartTime(Date stuStartTime) {
//        this.stuStartTime = stuStartTime;
//    }


    public String getStuEndTime() {
        return stuEndTime;
    }

    public void setStuEndTime(String stuEndTime) {
        this.stuEndTime = stuEndTime;
    }

    public long getAnswerTime() {
        return AnswerTime;
    }

    public void setAnswerTime(long answerTime) {
        AnswerTime = answerTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    private List<TestpaperQuestions>  testpaperQuestionsList= new ArrayList<TestpaperQuestions>();


    public String getSumbitState() {
        return sumbitState;
    }

    public void setSumbitState(String sumbitState) {
        this.sumbitState = sumbitState;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTcoName() {
        return tcoName;
    }

    public void setTcoName(String tcoName) {
        this.tcoName = tcoName;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getTcoId() {
        return tcoId;
    }

    public void setTcoId(String tcoId) {
        this.tcoId = tcoId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }


    public String getPaperscore() {
        return paperscore;
    }

    public void setPaperscore(String paperscore) {
        this.paperscore = paperscore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestPaperWorkId() {
        return testPaperWorkId;
    }

    public void setTestPaperWorkId(String testPaperWorkId) {
        this.testPaperWorkId = testPaperWorkId;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }

    public String getTscId() {
        return tscId;
    }

    public void setTscId(String tscId) {
        this.tscId = tscId;
    }

    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSuId() {
        return suId;
    }

    public void setSuId(String suId) {
        this.suId = suId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getSetScored() {
        return setScored;
    }

    public void setSetScored(String setScored) {
        this.setScored = setScored;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getMadeScore() {
        return madeScore;
    }

    public void setMadeScore(String madeScore) {
        this.madeScore = madeScore;
    }

    public String getStuScore() {
        return stuScore;
    }

    public void setStuScore(String stuScore) {
        this.stuScore = stuScore;
    }

    public ArrayList getScore() {
        return score;
    }

    public void setScore(ArrayList score) {
        this.score = score;
    }

    public ArrayList getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(ArrayList spaceName) {
        this.spaceName = spaceName;
    }

    public List<TestpaperQuestions> getTestpaperQuestionsList() {
        return testpaperQuestionsList;
    }

    public void setTestpaperQuestionsList(List<TestpaperQuestions> testpaperQuestionsList) {
        this.testpaperQuestionsList = testpaperQuestionsList;
    }

    public String getSetExam() {
        return setExam;
    }

    public void setSetExam(String setExam) {
        this.setExam = setExam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSumScore() {
        return sumScore;
    }

    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }

    @Override
    public String toString() {
        return "UserPaperWork{" +
                "createId='" + createId + '\'' +
                ", updateId='" + updateId + '\'' +
                ", schoolNo='" + schoolNo + '\'' +
                ", stuStartTime='" + stuStartTime + '\'' +
                ", stuEndTime='" + stuEndTime + '\'' +
                ", paperscore='" + paperscore + '\'' +
                ", userId='" + userId + '\'' +
                ", testPaperWorkId='" + testPaperWorkId + '\'' +
                ", sumbitState='" + sumbitState + '\'' +
                ", beiZhu='" + beiZhu + '\'' +
                ", cId='" + cId + '\'' +
                ", tcId='" + tcId + '\'' +
                ", tcName='" + tcName + '\'' +
                ", tgId='" + tgId + '\'' +
                ", tgName='" + tgName + '\'' +
                ", tscId='" + tscId + '\'' +
                ", tsId='" + tsId + '\'' +
                ", sysName='" + sysName + '\'' +
                ", suId='" + suId + '\'' +
                ", tcoId='" + tcoId + '\'' +
                ", tcoName='" + tcoName + '\'' +
                ", testName='" + testName + '\'' +
                ", type='" + type + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", headLine='" + headLine + '\'' +
                ", setScored='" + setScored + '\'' +
                ", testTime='" + testTime + '\'' +
                ", madeScore='" + madeScore + '\'' +
                ", stuScore='" + stuScore + '\'' +
                ", score=" + score +
                ", sumScore=" + sumScore +
                ", spaceName=" + spaceName +
                ", setExam='" + setExam + '\'' +
                ", time='" + time + '\'' +
                ", testpaperQuestionsList=" + testpaperQuestionsList +
                '}';
    }
}
