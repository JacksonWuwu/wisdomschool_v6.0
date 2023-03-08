package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.PptChapterUserService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PptChapterUserServiceImpl  extends BaseServiceImpl<PptChapterUserMapper, PptChapterUser>
        implements PptChapterUserService {
    @Autowired
    PptChapterUserMapper pptChapterUserMapper;

    @Override
    public PptChapterUser getBySidRid(PptChapterUser pptChapterUser) {
        return pptChapterUserMapper.getBySidRid(pptChapterUser);
    }

    @Override
    public List<PptChapterUser> listbycrids(List<String> crids) {
        return pptChapterUserMapper.listbycrids(crids);
    }

    @Override
    public List<PptChapterUser> listbycridsAndsid(PptChapterUser pptChapterUser) {
        return pptChapterUserMapper.listbycridsAndsid(pptChapterUser);
    }
}
