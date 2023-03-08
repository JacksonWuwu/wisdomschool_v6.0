package cn.wstom.student.entity;


import java.util.List;

public class Shuati {
    private MyQuestions myQuestions;
    private List<MyOptionAnswer> myOptionAnswerList;

    public MyQuestions getMyQuestions() {
        return myQuestions;
    }

    public void setMyQuestions(MyQuestions myQuestions) {
        this.myQuestions = myQuestions;
    }

    public List<MyOptionAnswer> getMyOptionAnswerList() {
        return myOptionAnswerList;
    }

    public void setMyOptionAnswerList(List<MyOptionAnswer> myOptionAnswerList) {
        this.myOptionAnswerList = myOptionAnswerList;
    }
}
