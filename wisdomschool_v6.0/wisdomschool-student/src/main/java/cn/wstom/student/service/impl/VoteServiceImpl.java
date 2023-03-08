package cn.wstom.student.service.impl;


import cn.wstom.student.entity.Vote;
import cn.wstom.student.entity.VoteOptionSubmit;
import cn.wstom.student.mapper.VoteMapper;
import cn.wstom.student.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl extends BaseServiceImpl<VoteMapper, Vote> implements VoteService {

    @Autowired
    private VoteMapper voteMapper;

    @Override
    public Boolean updataVoteStudent(VoteOptionSubmit voteOptionSubmit) {
        return voteMapper.updataVoteStudent(voteOptionSubmit);
    }

    @Override
    public List<VoteOptionSubmit> listbyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit) {
        return voteMapper.listbyVoteOptionSubmit(voteOptionSubmit);
    }

    @Override
    public boolean savevoteOptionSubmitsBatch(List<VoteOptionSubmit> list) {
        return voteMapper.savevoteOptionSubmitsBatch(list);
    }

    @Override
    public VoteOptionSubmit getonebyVoteOptionSubmit(VoteOptionSubmit voteOptionSubmit) {
        return voteMapper.getonebyVoteOptionSubmit(voteOptionSubmit);
    }
}
