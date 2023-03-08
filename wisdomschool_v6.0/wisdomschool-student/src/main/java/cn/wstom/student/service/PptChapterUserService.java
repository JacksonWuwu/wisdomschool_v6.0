package cn.wstom.student.service;





import cn.wstom.student.entity.PptChapterUser;

import java.util.List;

public interface PptChapterUserService extends BaseService<PptChapterUser> {
    PptChapterUser getBySidRid(PptChapterUser pptChapterUser);
    List<PptChapterUser> listbycrids(List<String> crids);
    List<PptChapterUser> listbycridsAndsid(PptChapterUser pptChapterUser);
}
