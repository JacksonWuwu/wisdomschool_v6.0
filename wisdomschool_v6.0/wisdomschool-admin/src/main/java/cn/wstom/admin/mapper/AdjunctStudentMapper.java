package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.AdjunctStudent;


import java.util.List;

public interface AdjunctStudentMapper extends BaseMapper<AdjunctStudent> {
    List<AdjunctStudent> selectListBysids(List<String> sids);
    Boolean updateByAidAndSid(AdjunctStudent adjunctStudent);
    AdjunctStudent selectBySid(AdjunctStudent adjunctStudent);
    List<AdjunctStudent> selectListByaid(String adjid);
    List<AdjunctStudent> selectListByAdjunctStudent(AdjunctStudent adjunctStudent);

    void deleteByAid(String aid);

}
