package cn.wstom.admin.service;

import cn.wstom.admin.entity.AdjunctStudent;


import java.util.List;

public interface AdjunctStudentService extends BaseService<AdjunctStudent> {
    List<AdjunctStudent> selectListBysids(List<String> sids);
    AdjunctStudent selectBySid(AdjunctStudent adjunctStudent);
    Boolean updateByAidAndSid(AdjunctStudent adjunctStudent);
    List<AdjunctStudent> selectListByaid(String adjid);
    List<AdjunctStudent> selectListByAdjunctStudent(AdjunctStudent adjunctStudent);
    void deleteByAid(String aid);
}
