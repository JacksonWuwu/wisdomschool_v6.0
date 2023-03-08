package cn.wstom.student.service.impl;


import cn.wstom.student.entity.ShuatiHistory;
import cn.wstom.student.mapper.ShuatiHistoryMapper;
import cn.wstom.student.service.ShuatiHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ShuatiHistoryServiceImpl extends BaseServiceImpl<ShuatiHistoryMapper, ShuatiHistory> implements ShuatiHistoryService {
    @Resource
    private ShuatiHistoryMapper shuatiHistoryMapper;

    /**
     * 通过用户ID查询
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public ShuatiHistory selectByUserId(String userId) {
        return shuatiHistoryMapper.selectByUserId(userId);
    }
}

