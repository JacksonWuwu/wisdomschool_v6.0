package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.ChapterResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程章节 数据层
 *
 * @author dws
 * @date 20190223
 */
public interface ChapterResourceMapper extends BaseMapper<ChapterResource> {

    List<ChapterResource> selectByCidAndSid(@Param("userId") String userId,
                                            @Param("courseId") String courseId,
                                            @Param("chapterId") String chapterId);

    int insertBackId(ChapterResource chapterResource);
    List<ChapterResource> selectTestPaperOneId(@Param("id") String id);
}
