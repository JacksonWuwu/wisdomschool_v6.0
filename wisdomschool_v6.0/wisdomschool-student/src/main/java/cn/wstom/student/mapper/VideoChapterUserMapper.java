package cn.wstom.student.mapper;


import cn.wstom.student.entity.VideoChapterUser;

/**
* 用户观看 数据层
*
* @author dws
* @date 20200204
*/
public interface VideoChapterUserMapper extends BaseMapper<VideoChapterUser> {
    VideoChapterUser selectByVideoName(VideoChapterUser videoChapterUser);
}
