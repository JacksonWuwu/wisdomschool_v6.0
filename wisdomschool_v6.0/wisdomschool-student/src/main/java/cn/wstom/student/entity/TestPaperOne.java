package cn.wstom.student.entity;


import lombok.Data;

/**
 * 试卷表 tk_test_paper
 *
 * @author hzh
 * @date 20190223
 */
@Data
public class TestPaperOne extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 创建人Id
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
     * 结束时间
     */
    private String endTime;
    /**
     * 标题
     */
    private String headline;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 试卷名
     */
    private String testName;
    /**
     * 年份
     */
    private String testYear;
    /**
     * 结束人数
     */
    private Integer endNum;
    /**
     * 在考人数
     */
    private Integer reviewNum;
    /**
     * 开始人数
     */
    private Integer startNum;
    /**
     * 状态 0：已组 1：未组
     */
    private String state;
    /**
     * 备注
     */
    private String beiZhu;
    /**
     * 课程id
     */
    private String cId;
    /**
     * 年级
     */
    private String tgId;
    /**
     * 年名称级
     */
    private String tgName;
    /**
     * 班级
     */
    private String tcId;
    /**
     * 班级名称
     */
    private String tcName;
    /**
     * 班级所绑定的课程
     */
    private String tcoId;
    /**
     * 班级所绑定的课程名称
     */
    private String tcoName;
    /**
     * 学生绑定的课程id
     */
    private String tscId;
    /**
     * 学生id
     */
    private String tsId;
    /**
     * 学生名称
     */
    private String sysName;
    /**
     * 用户ID
     */
    private String suId;
    /**
     * 所有小节的id
     */
    private String chapterIds;
    /**
     * 每种题型的数量
     */
    private String selecttype;
    /**
     * 每种题型的分数
     */
    private String selecttypescore;
    /**
     * 题型的id 属于哪种类型题 比如选择题还是填空题
     */
    private String txid;
    /**
     * 具体专业的id
     */
    private String subid;
    /**
     * 难度等级
     */
    private String dif;

    /**
     * 课程ID
     */
    private String coursrId;
    /**
     * 关联章节试卷的名
     */
    private String name;
    /**
     * 试卷类型
     */
    private String type;
    /**
     * 是否已改卷
     */
    private String setScored;
    /**
     * 试卷分数
     */
    private String score;
    /**
     * 考试时长
     */
    private String time;
    /**
     * 组卷规则 1：随机 0：固定
     */
    private String rule;
    /**
     * 设置开始考试 0，否 1，是
     */
    private String setExam;
    /**
     * 试卷id
     */
    private String testPaper;
    /**
     * 发送范围
     */
    private String scope;

    /**
     * 学生考试状态
     * 0:未参与  1：已参与
     * @return
     */

    private int user_state;

    /**
     * 考试提交状态
     * @return
     */

    private String sumbitState;

    public String getSumbitState() {
        return sumbitState;
    }

    public void setSumbitState(String sumbitState) {
        this.sumbitState = sumbitState;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }



    public int getUser_state() {
        return user_state;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(String testPaper) {
        this.testPaper = testPaper;
    }

    public String getCoursrId() {
        return coursrId;
    }

    public void setCoursrId(String coursrId) {
        this.coursrId = coursrId;
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



    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestYear() {
        return testYear;
    }

    public void setTestYear(String testYear) {
        this.testYear = testYear;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getTcoId() {
        return tcoId;
    }

    public void setTcoId(String tcoId) {
        this.tcoId = tcoId;
    }

    public String getTcoName() {
        return tcoName;
    }

    public void setTcoName(String tcoName) {
        this.tcoName = tcoName;
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

    public String getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(String chapterIds) {
        this.chapterIds = chapterIds;
    }

    public String getSelecttype() {
        return selecttype;
    }

    public void setSelecttype(String selecttype) {
        this.selecttype = selecttype;
    }

    public String getSelecttypescore() {
        return selecttypescore;
    }

    public void setSelecttypescore(String selecttypescore) {
        this.selecttypescore = selecttypescore;
    }

    public String getTid() {
        return txid;
    }

    public void setTid(String txid) {
        this.txid = txid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetScored() {
        return setScored;
    }

    public void setSetScored(String setScored) {
        this.setScored = setScored;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getSetExam() {
        return setExam;
    }

    public void setSetExam(String setExam) {
        this.setExam = setExam;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    @Override
    public String toString() {
        return "TestPaper{" +
                "id='" + id + '\'' +
                "createId='" + createId + '\'' +
                "createBy='" + createBy + '\'' +
                ", updateId='" + updateId + '\'' +
                ", schoolNo='" + schoolNo + '\'' +
                ", endTime='" + endTime + '\'' +
                ", headline='" + headline + '\'' +
                ", startTime='" + startTime + '\'' +
                ", testName='" + testName + '\'' +
                ", testYear='" + testYear + '\'' +
                ", endNum=" + endNum +
                ", reviewNum=" + reviewNum +
                ", startNum=" + startNum +
                ", state='" + state + '\'' +
                ", beiZhu='" + beiZhu + '\'' +
                ", cId='" + cId + '\'' +
                ", tgId='" + tgId + '\'' +
                ", tgName='" + tgName + '\'' +
                ", tcId='" + tcId + '\'' +
                ", tcName='" + tcName + '\'' +
                ", tcoId='" + tcoId + '\'' +
                ", tcoName='" + tcoName + '\'' +
                ", tscId='" + tscId + '\'' +
                ", tsId='" + tsId + '\'' +
                ", sysName='" + sysName + '\'' +
                ", suId='" + suId + '\'' +
                ", chapterIds='" + chapterIds + '\'' +
                ", selecttype='" + selecttype + '\'' +
                ", selecttypescore='" + selecttypescore + '\'' +
                ", txid='" + txid + '\'' +
                ", subid='" + subid + '\'' +
                ", dif='" + dif + '\'' +
                ", coursrId='" + coursrId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", setScored='" + setScored + '\'' +
                ", score='" + score + '\'' +
                ", time='" + time + '\'' +
                ", rule='" + rule + '\'' +
                ", setExam='" + setExam + '\'' +
                '}';
    }
}
