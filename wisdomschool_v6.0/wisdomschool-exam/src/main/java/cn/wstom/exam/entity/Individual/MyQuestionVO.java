package cn.wstom.exam.entity.Individual;


import cn.wstom.exam.annotation.Excel;
import cn.wstom.exam.entity.Chapter;
import cn.wstom.exam.entity.Course;
import cn.wstom.exam.entity.MyQuestions;
import cn.wstom.exam.entity.MyTitleType;
import lombok.Data;

@Data
public class MyQuestionVO<Srting> extends MyQuestions {
    private static final long serialVersionUID = -4765542647341463846L;




    /**
     * 题型
     */
    @Excel(name = "题型（必填）", comboField = "titleTypeName", targetAttr = "titleTypeName")
    private MyTitleType myTitleType;

    /**
     * 题目信息
     */
    @Excel(name = "题目（必填）", comboField = "title", targetAttr = "title")
    private MyQuestions myQuestions;

    /**
     * 题目信息
     */
    @Excel(name = "A是否答案")
    Srting myOptionAstans;
  @Excel(name = "选项A")
    Srting myOptionA;
    @Excel(name = "B是否答案")
    Srting myOptionBstans;
    @Excel(name = "选项B")
    Srting myOptionB;
    @Excel(name = "C是否答案")
    Srting myOptionCstans;
    @Excel(name = "选项C")
    Srting myOptionC;

    public Srting getMyOptionAstans() {
        return myOptionAstans;
    }

    public void setMyOptionAstans(Srting myOptionAstans) {
        this.myOptionAstans = myOptionAstans;
    }

    public Srting getMyOptionBstans() {
        return myOptionBstans;
    }

    public void setMyOptionBstans(Srting myOptionBstans) {
        this.myOptionBstans = myOptionBstans;
    }

    public Srting getMyOptionCstans() {
        return myOptionCstans;
    }

    public void setMyOptionCstans(Srting myOptionCstans) {
        this.myOptionCstans = myOptionCstans;
    }

    public Srting getMyOptionDstans() {
        return myOptionDstans;
    }

    public void setMyOptionDstans(Srting myOptionDstans) {
        this.myOptionDstans = myOptionDstans;
    }

    @Excel(name = "D是否答案")
    Srting myOptionDstans;
    @Excel(name = "选项D")
    Srting myOptionD;

    public Srting getMyOptionA() {
        return myOptionA;
    }

    public void setMyOptionA(Srting myOptionA) {
        this.myOptionA = myOptionA;
    }

    public Srting getMyOptionB() {
        return myOptionB;
    }

    public void setMyOptionB(Srting myOptionB) {
        this.myOptionB = myOptionB;
    }

    public Srting getMyOptionC() {
        return myOptionC;
    }

    public void setMyOptionC(Srting myOptionC) {
        this.myOptionC = myOptionC;
    }

    public Srting getMyOptionD() {
        return myOptionD;
    }

    public void setMyOptionD(Srting myOptionD) {
        this.myOptionD = myOptionD;
    }

    /**
     * 课程
     */
    @Excel(name = "课程(必填)", comboField = "name", targetAttr = "name")
    private Course course;
    /**
     * 所在章节
     */
    @Excel(name = "知识点(必填)", comboField = "name", targetAttr = "name")
    private Chapter chapter;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MyQuestions getMyQuestions() {
        return myQuestions;
    }

    public void setMyQuestions(MyQuestions myQuestions) {
        this.myQuestions = myQuestions;
    }

    public MyTitleType getMyTitleType() {
        return myTitleType;
    }

    public void setMyTitleType(MyTitleType myTitleType) {
        this.myTitleType = myTitleType;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

 }
