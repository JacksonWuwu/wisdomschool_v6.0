package cn.wstom.student.service;





import cn.wstom.student.entity.Vote;
import cn.wstom.student.entity.VoteOptionSubmit;

import java.util.List;


public interface VoteService extends BaseService<Vote> {
    /**
     * 更新学生投票
     * @return
     */
    Boolean updataVoteStudent(VoteOptionSubmit voteOptionSubmit);


    List<VoteOptionSubmit> listbyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit);


    boolean savevoteOptionSubmitsBatch(List<VoteOptionSubmit> list);

    VoteOptionSubmit getonebyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit);
}
