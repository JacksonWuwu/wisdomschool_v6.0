package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Vote extends BaseEntity {



    private List<MyQuestions> myQuestionsList;
    private List<VoteAnswer> voteAnswerList;

    private String title;

    /**
     * 选项与答案
     */
    @Excel(name = "答案(必填)")
    private String myoptionAnswerArr;


    private String myoptionAnswerArrContent;


    /**
     * 科目
     */
    private String xzsubjectsId;


    /**
     *状态
     */
    private Integer ststatus;

    /**
     *科目名称
     */
    private String subjectsName;


    /**
     *科目Id
     */
    private String subjectsId;

    /**
     *修改者Id
     */
    String updateId;
    /**
     *添加者Id
     */
    String createId;


    /*
     *学生id数组
     * */
    private String userId;

    /**
     * 班级id
     */
    private String cid;
    /*
     * 班级名字
     * */
    private List<String> clbumName;
    /*
     * 班级
     * */
    private Clbum clbum;

    private int optionSize;
}
