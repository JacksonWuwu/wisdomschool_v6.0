package cn.wstom.admin.service;




import cn.wstom.admin.entity.VideoChapter;

import java.util.List;

/**
* 章节视频 服务层
*
* @author dws
* @date 20200204
*/
public interface VideoChapterService extends BaseService<VideoChapter> {
    VideoChapter selectByRcId(Integer rcId);
    List<Integer> selecttobeState(String tcid);
    boolean updatebytcid(VideoChapter videoChapter);
}
