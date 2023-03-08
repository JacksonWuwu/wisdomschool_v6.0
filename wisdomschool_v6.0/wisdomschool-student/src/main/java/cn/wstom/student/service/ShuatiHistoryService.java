package cn.wstom.student.service;


import cn.wstom.student.entity.ShuatiHistory;

/**
 * 刷题记录
 *
 * @author lph
 * @date 20191008
 */

public interface ShuatiHistoryService extends BaseService<ShuatiHistory> {

    /**
     * 通过用户id查询
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    ShuatiHistory selectByUserId(String userId);
}
