package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.Preview;
import cn.wstom.admin.entity.PreviewChapter;
import cn.wstom.admin.entity.PreviewStudent;

import java.util.List;


public interface PreviewMapper extends BaseMapper<Preview> {
    int addreturn(Preview preview);
    List<String> listforcid(Preview preview);
    int insertPreviewChapter(List<PreviewChapter> list);
    int insertPreviewStudent(List<PreviewStudent> list);
    List<String> listfolistBySidAndTcidrcid(PreviewStudent preview);

}
