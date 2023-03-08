package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.AdjunctStudent;
import cn.wstom.admin.mapper.AdjunctStudentMapper;
import cn.wstom.admin.service.AdjunctStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjunctStudentServiceImpl extends BaseServiceImpl<AdjunctStudentMapper, AdjunctStudent>
        implements AdjunctStudentService {
    @Autowired
    private AdjunctStudentMapper adjunctStudentMapper;
    @Override
    public List<AdjunctStudent> selectListBysids(List<String> sids) {
        return adjunctStudentMapper.selectListBysids(sids);
    }

    @Override
    public AdjunctStudent selectBySid(AdjunctStudent adjunctStudent) {
        return adjunctStudentMapper.selectById(adjunctStudent);
    }

    @Override
    public Boolean updateByAidAndSid(AdjunctStudent adjunctStudent) {
        return adjunctStudentMapper.updateByAidAndSid(adjunctStudent);
    }

    @Override
    public List<AdjunctStudent> selectListByaid(String adjid) {
        return adjunctStudentMapper.selectListByaid(adjid);
    }

    @Override
    public List<AdjunctStudent> selectListByAdjunctStudent(AdjunctStudent adjunctStudent) {
        List<AdjunctStudent> adjunctStudentList=adjunctStudentMapper.selectListByAdjunctStudent(adjunctStudent);
        return adjunctStudentList;
    }

    @Override
    public void deleteByAid(String aid) {
        adjunctStudentMapper.deleteByAid(aid);
    }
}
