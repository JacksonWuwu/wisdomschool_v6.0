package cn.wstom.student.service;





import cn.wstom.student.entity.VideoChapterUser;
import cn.wstom.student.entity.VideoChapterUserVo;

import java.util.List;
import java.util.Map;

/**
* 用户观看 服务层
*
* @author dws
* @date 20200204
*/
public interface VideoChapterUserService extends BaseService<VideoChapterUser> {

    /**
     * 组合获取
     * @param videoChapterUserVo
     * @return
     */
    List<Map<String, Object>> listVo(VideoChapterUserVo videoChapterUserVo);

    List<Map<String, Object>> listVo(int userId);

    /**
     *根据名字和userid找视频
     */

    VideoChapterUser selectByVideoName(VideoChapterUser videoChapterUser);
}
