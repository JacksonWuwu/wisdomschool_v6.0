package cn.wstom.student.mapper;





import cn.wstom.student.entity.VideoChapter;

import java.io.Serializable;
import java.util.List;

/**
* 章节视频 数据层
*
* @author dws
* @date 20200204
*/
public interface VideoChapterMapper extends BaseMapper<VideoChapter> {
    VideoChapter selectByRcId(Serializable id);
    List<Integer> selecttobeState(String tcid);
    boolean updatebytcid(VideoChapter videoChapter);
}
