package cn.wstom.admin.service;



import cn.wstom.admin.entity.Preview;
import cn.wstom.admin.entity.PreviewChapter;
import cn.wstom.admin.entity.PreviewStudent;

import java.util.List;

public interface PreviewService extends BaseService<Preview> {
    int addreturn(Preview preview);
    int insertPreviewChapter(List<PreviewChapter> list);
    List<String> listforcid(Preview preview);
    Boolean insertPreviewStudent(List<PreviewStudent> list);
    List<String> listBySidAndTcid(PreviewStudent previewStudent);
}
