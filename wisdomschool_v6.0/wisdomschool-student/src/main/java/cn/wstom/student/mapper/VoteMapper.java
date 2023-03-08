package cn.wstom.student.mapper;


import cn.wstom.student.entity.Vote;
import cn.wstom.student.entity.VoteOptionSubmit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteMapper extends BaseMapper<Vote> {

    /**
     * 按条件查询问题
     */
    @Override
    List<Vote> selectList(Vote vote);

    /**
     * 查询未整理的代码
     * @return
     */
    List<Vote> unList(Vote vote);

    /**
     * 由Id查询未整理的代码
     * @param id
     * @return
     */
    Vote getUnListById(String id);

    /**
     * 查询一门科目中的一种题型的全部试题
     *
     * @param titleTypeId
     * @param xzsubjectsId
     * @return
     */
    List<Vote> selectQuestionByTid(@Param("titleTypeId") String titleTypeId, @Param("xzsubjectsId") String xzsubjectsId);

    /**
     * 更新学生投票
     * @return
     */
    Boolean updataVoteStudent(VoteOptionSubmit voteOptionSubmit);


    List<VoteOptionSubmit> listbyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit);


    boolean savevoteOptionSubmitsBatch(List<VoteOptionSubmit> list);


    VoteOptionSubmit getonebyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit);
}
