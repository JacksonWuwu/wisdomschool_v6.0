package cn.wstom.admin.service.impl;


import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程章节资源 服务层实现
 *
 * @author dws
 * @date 20190223
 */
@Service
public class ChapterResourceServiceImpl extends BaseServiceImpl<ChapterResourceMapper, ChapterResource>
        implements ChapterResourceService {

    @Resource
    private ChapterResourceMapper chapterResourceMapper;

    @Override
    public List<ChapterResource> selectByCidAndSid(String userId, String courseId, String chapterId) {
        return chapterResourceMapper.selectByCidAndSid(userId, courseId, chapterId);
    }

    @Override
    public int saveBackId(ChapterResource chapterResource) {
        return chapterResourceMapper.insertBackId(chapterResource);
    }

    @Override
    public List<ChapterResource> selectTestPaperOneId(String id) {
        return chapterResourceMapper.selectTestPaperOneId(id);
    }
}
