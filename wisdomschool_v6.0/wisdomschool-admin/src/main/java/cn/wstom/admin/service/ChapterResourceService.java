package cn.wstom.admin.service;

import cn.wstom.admin.entity.ChapterResource;


import java.util.List;

/**
 * 课程章节 服务层
 *
 * @author dws
 * @date 20190223
 */
public interface ChapterResourceService extends BaseService<ChapterResource> {

    /**
     * 根据学生id和课程id查找章节资源
     *
     * @param userId
     * @param courseId
     * @return
     */
    List<ChapterResource> selectByCidAndSid(String userId, String courseId, String chapterId);

    int saveBackId(ChapterResource chapterResource);

    List<ChapterResource> selectTestPaperOneId(String id);
}
