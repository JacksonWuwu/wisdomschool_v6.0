package cn.wstom.student.mapper;


import cn.wstom.student.entity.ShuatiHistory;

public interface ShuatiHistoryMapper extends BaseMapper<ShuatiHistory> {

    /**
     * 通过用户id查询
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    ShuatiHistory selectByUserId(String userId);
}
