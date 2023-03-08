package cn.wstom.admin.service;



import cn.wstom.admin.entity.PptChapterUser;

import java.util.List;

public interface PptChapterUserService extends BaseService<PptChapterUser> {
    PptChapterUser getBySidRid(PptChapterUser pptChapterUser);
    List<PptChapterUser> listbycrids(List<String> crids);
    List<PptChapterUser> listbycridsAndsid(PptChapterUser pptChapterUser);
}
