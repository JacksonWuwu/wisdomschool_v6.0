package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.VideoChapterUser;


/**
* 用户观看 数据层
*
* @author dws
* @date 20200204
*/
public interface VideoChapterUserMapper extends BaseMapper<VideoChapterUser> {
    VideoChapterUser selectByVideoName(VideoChapterUser videoChapterUser);
}
