package cn.wstom.student.service.impl;


import cn.wstom.student.entity.PptChapterUser;
import cn.wstom.student.mapper.PptChapterUserMapper;
import cn.wstom.student.service.PptChapterUserService;
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
