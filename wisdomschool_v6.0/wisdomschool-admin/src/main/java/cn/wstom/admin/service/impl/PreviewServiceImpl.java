package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreviewServiceImpl extends BaseServiceImpl<PreviewMapper, Preview>
        implements PreviewService {
    @Autowired
    private PreviewMapper previewMapper;

    @Override
    public int addreturn(Preview preview) {
        previewMapper.addreturn(preview);
        int i = Integer.valueOf(preview.getId());
        return i;

    }

    @Override
    public int insertPreviewChapter(List<PreviewChapter> list) {
        return previewMapper.insertPreviewChapter(list);
    }

    @Override
    public List<String> listforcid(Preview preview) {
        return previewMapper.listforcid(preview);
    }

    @Override
    public Boolean insertPreviewStudent(List<PreviewStudent> list) {

        return d(previewMapper.insertPreviewStudent(list));
    }

    @Override
    public List<String> listBySidAndTcid(PreviewStudent previewStudent) {
        return previewMapper.listfolistBySidAndTcidrcid(previewStudent);
    }

    boolean d(Integer result) {
        return null != result && result >= 1;
    }
}
