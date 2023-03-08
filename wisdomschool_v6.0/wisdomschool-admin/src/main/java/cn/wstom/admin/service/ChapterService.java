package cn.wstom.admin.service;

import cn.wstom.admin.entity.Chapter;
import cn.wstom.admin.entity.Comment;
import cn.wstom.admin.entity.PageVo;


import java.util.List;
import java.util.Map;

/**
 * 课程章节 服务层
 *
 * @author dws
 * @date 20190223
 */
public interface ChapterService extends BaseService<Chapter> {
    /**
     * 根据条件查章节
     */
    List<Chapter> selectList(Chapter chapter);

    /**
     * 根据预习id查章节
     */
    List<Chapter> selectListByPreviewid(Chapter chapter);

    /**
     * 根据预习id查章节
     */
    List<Map<String, Object>> selectListByPreviewidTwo(Chapter chapter);


    /**
     * 获取课程树
     *
     * @param cid
     * @return
     */
    List<Map<String, Object>> getCourseChapterTree(String cid);
    List<Map<String, Object>> getCourseTree(String cid);
    List<Map<String, Object>> getCourseChapterInfoTree(String cid);

    PageVo<Comment> getCourseCommentListPage(String courseId, String userId, String createTime, String orderBy, String order, String chapterId, int p, int rows, Integer userType);
}
