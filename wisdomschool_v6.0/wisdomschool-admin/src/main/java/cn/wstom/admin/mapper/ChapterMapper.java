package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Comment;
import cn.wstom.admin.entity.Chapter;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程章节 数据层
 *
 * @author dws
 * @date 20190223
 */
public interface ChapterMapper extends BaseMapper<Chapter> {
    /**
     * 根据条件查章节
     */
    @Override
    List<Chapter> selectList(Chapter chapter);


    /**
     * 根据预习id查章节
     */
    List<Chapter> selectListByPreviewid(Chapter chapter);

    /**
     * 根据课程编号删除章节
     *
     * @param cid
     * @return
     */
    boolean deleteByCid(String cid);

    List<Comment> getCourseCommentList(@Param("courseId") String courseId,
                                       @Param("userId") String userId,
                                       @Param("createTime") String createTime,
                                       @Param("chapterId") String chapterId,
                                       @Param("orderBy") String orderBy,
                                       @Param("order") String order,
                                       @Param("offset") int offset,
                                       @Param("rows") int rows,
                                       @Param("userType") Integer userType);

    int getCourseCommentCount(@Param("courseId") String courseId,
                              @Param("userId") String userId,
                              @Param("createTime") String createTime,
                              @Param("chapterId") String chapterId);
}
