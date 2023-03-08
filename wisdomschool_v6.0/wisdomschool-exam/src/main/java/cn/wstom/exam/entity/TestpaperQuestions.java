package cn.wstom.exam.entity;

import lombok.Data;

import java.util.List;

/**
 * 试卷题目表 tk_testpaper_questions
 *
 * @author hzh
 * @date 20190223
 */
@Data
public class TestpaperQuestions extends BaseEntity {
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
     * 难度
     */
    private String difficulty;
    /**
     * 解析
     */
    private String parsing;
    /**
     * 试题已曝光数
     */
    private String qexposed;
    /**
     * 试题最大曝光
     */
    private String qmaxexposure;
    /**
     * 状态0：待审核、1：审核通过、2：审核不通过
     */
    private Integer ststatus;
    /**
     * 名
     */
    private String title;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 章Id
     */
    private String chapterId;
    /**
     * 题型Id
     */
    private String titleTypeId;
    /**
     *
     */
    private String xzsubjectsId;
    /**
     * 答案
     */
    private String testPaperOptionAnswerArr;
    /**
     * 题目分数
     */
    private Integer questionScore;

    /**
     * 题型模板
     */
    private String  tQuestiontemplateNum;
    /**
     * 题型模板ID
     */
    private String tQuestiontpubilcTitleId;
    /**
     * 题型名称
     */
    private String titleTypeName;
    /**
     * 题目答案List
     */
    private List<TestpaperOptinanswer> testpaperOptinanswerList;

    /**
     * 学生答案List
     */
    private List<TestStuoptionanswer>testStuoptionanswerList;
    /**
     * 章节测试学生答案
     */
    private List<StuOptionanswer> stuOptionanswerList;
    /**
     * 章节考试学生答案
     */
    private List<StuOptionExamanswer> stuOptionExamanswerList;
    /**
     *
     */
    private StuQuestionScore stuQuestionScore;


    private ChapterQuestionScore chapterQuestionScore;
    /**
     * 题型编号
     */
    private  String titleTypeNum;
    /**
     * 题目答案
     */
   private List<CoursetestStuoptionanswer> coursetestStuoptionanswerList;
    /**
     * 题目共做人数
     */
   private Integer doneStu;
    /**
     * 做错的人数
     */
   private Integer errorDone;

    /**
     * 个人题目Id
     */
    private Integer personalQuestionId;

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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getParsing() {
        return parsing;
    }

    public void setParsing(String parsing) {
        this.parsing = parsing;
    }

    public String getQexposed() {
        return qexposed;
    }

    public void setQexposed(String qexposed) {
        this.qexposed = qexposed;
    }

    public String getQmaxexposure() {
        return qmaxexposure;
    }

    public void setQmaxexposure(String qmaxexposure) {
        this.qmaxexposure = qmaxexposure;
    }

    public Integer getStstatus() {
        return ststatus;
    }

    public void setStstatus(Integer ststatus) {
        this.ststatus = ststatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitleTypeId() {
        return titleTypeId;
    }

    public void setTitleTypeId(String titleTypeId) {
        this.titleTypeId = titleTypeId;
    }

    public String getXzsubjectsId() {
        return xzsubjectsId;
    }

    public void setXzsubjectsId(String xzsubjectsId) {
        this.xzsubjectsId = xzsubjectsId;
    }

    public String getTestPaperOptionAnswerArr() {
        return testPaperOptionAnswerArr;
    }

    public void setTestPaperOptionAnswerArr(String testPaperOptionAnswerArr) {
        this.testPaperOptionAnswerArr = testPaperOptionAnswerArr;
    }

    public Integer getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }

    public String gettQuestiontemplateNum() {
        return tQuestiontemplateNum;
    }

    public void settQuestiontemplateNum(String tQuestiontemplateNum) {
        this.tQuestiontemplateNum = tQuestiontemplateNum;
    }

    public String gettQuestiontpubilcTitleId() {
        return tQuestiontpubilcTitleId;
    }

    public void settQuestiontpubilcTitleId(String tQuestiontpubilcTitleId) {
        this.tQuestiontpubilcTitleId = tQuestiontpubilcTitleId;
    }

    public String getTitleTypeName() {
        return titleTypeName;
    }

    public void setTitleTypeName(String titleTypeName) {
        this.titleTypeName = titleTypeName;
    }

    public List<TestpaperOptinanswer> getTestpaperOptinanswerList() {
        return testpaperOptinanswerList;
    }

    public void setTestpaperOptinanswerList(List<TestpaperOptinanswer> testpaperOptinanswerList) {
        this.testpaperOptinanswerList = testpaperOptinanswerList;
    }

    public List<TestStuoptionanswer> getTestStuoptionanswerList() {
        return testStuoptionanswerList;
    }

    public void setTestStuoptionanswerList(List<TestStuoptionanswer> testStuoptionanswerList) {
        this.testStuoptionanswerList = testStuoptionanswerList;
    }


    public String getTitleTypeNum() {
        return titleTypeNum;
    }

    public void setTitleTypeNum(String titleTypeNum) {
        this.titleTypeNum = titleTypeNum;
    }

    public StuQuestionScore getStuQuestionScore() {
        return stuQuestionScore;
    }

    public void setStuQuestionScore(StuQuestionScore stuQuestionScore) {
        this.stuQuestionScore = stuQuestionScore;
    }

    public List<CoursetestStuoptionanswer> getCoursetestStuoptionanswerList() {
        return coursetestStuoptionanswerList;
    }

    public void setCoursetestStuoptionanswerList(List<CoursetestStuoptionanswer> coursetestStuoptionanswerList) {
        this.coursetestStuoptionanswerList = coursetestStuoptionanswerList;
    }

    public List<StuOptionanswer> getStuOptionanswerList() {
        return stuOptionanswerList;
    }
    public List<StuOptionExamanswer> getStuOptionExamanswerList() {
        return stuOptionExamanswerList;
    }
    public void setStuOptionanswerList(List<StuOptionanswer> stuOptionanswerList) {
        this.stuOptionanswerList = stuOptionanswerList;
    }
    public void setStuOptionExamanswerList(List<StuOptionExamanswer> stuOptionExamanswerList) {
        this.stuOptionExamanswerList = stuOptionExamanswerList;
    }

    public ChapterQuestionScore getChapterQuestionScore() {
        return chapterQuestionScore;
    }

    public void setChapterQuestionScore(ChapterQuestionScore chapterQuestionScore) {
        this.chapterQuestionScore = chapterQuestionScore;
    }

    public Integer getDoneStu() {
        return doneStu;
    }

    public void setDoneStu(Integer doneStu) {
        this.doneStu = doneStu;
    }

    public Integer getErrorDone() {
        return errorDone;
    }

    public void setErrorDone(Integer errorDone) {
        this.errorDone = errorDone;
    }

    @Override
    public String toString() {
        return "TestpaperQuestions{" +
                "createId='" + createId + '\'' +
                ", updateId='" + updateId + '\'' +
                ", schoolNo='" + schoolNo + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", parsing='" + parsing + '\'' +
                ", qexposed='" + qexposed + '\'' +
                ", qmaxexposure='" + qmaxexposure + '\'' +
                ", ststatus=" + ststatus +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", chapterId='" + chapterId + '\'' +
                ", titleTypeId='" + titleTypeId + '\'' +
                ", xzsubjectsId='" + xzsubjectsId + '\'' +
                ", testPaperOptionAnswerArr='" + testPaperOptionAnswerArr + '\'' +
                ", questionScore=" + questionScore +
                ", tQuestiontemplateNum='" + tQuestiontemplateNum + '\'' +
                ", tQuestiontpubilcTitleId='" + tQuestiontpubilcTitleId + '\'' +
                ", titleTypeName='" + titleTypeName + '\'' +
                ", testpaperOptinanswerList=" + testpaperOptinanswerList +
                ", testStuoptionanswerList=" + testStuoptionanswerList +
                ", stuOptionanswerList=" + stuOptionanswerList +
                ", stuOptionExamanswerList=" + stuOptionExamanswerList +
                ", stuQuestionScore=" + stuQuestionScore +
                ", chapterQuestionScore=" + chapterQuestionScore +
                ", titleTypeNum='" + titleTypeNum + '\'' +
                ", coursetestStuoptionanswerList=" + coursetestStuoptionanswerList +
                ", doneStu=" + doneStu +
                ", errorDone=" + errorDone +
                '}';
    }
}
