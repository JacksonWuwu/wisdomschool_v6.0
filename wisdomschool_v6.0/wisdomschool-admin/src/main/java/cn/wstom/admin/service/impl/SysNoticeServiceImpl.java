package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysNotice;
import cn.wstom.admin.mapper.SysNoticeMapper;
import cn.wstom.admin.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公告 服务层实现
 *
 * @author dws
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Autowired
    private SysNoticeMapper noticeMapper;

}
