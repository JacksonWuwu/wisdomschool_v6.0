package cn.wstom.student.service.impl;



import cn.wstom.student.entity.*;
import cn.wstom.student.feign.AdminService;
import cn.wstom.student.mapper.VideoChapterMapper;
import cn.wstom.student.mapper.VideoChapterUserMapper;
import cn.wstom.student.mapper.VideoChapterUserVoMapper;
import cn.wstom.student.service.VideoChapterUserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 用户观看 服务层实现
*
* @author dws
* @date 20200204
*/
@Service
public class VideoChapterUserServiceImpl extends
        BaseServiceImpl<VideoChapterUserMapper, VideoChapterUser> implements VideoChapterUserService {

    @Autowired
    private VideoChapterUserMapper videoChapterUserMapper;
    @Autowired
    private VideoChapterMapper videoChapterMapper;
    @Autowired
    private VideoChapterUserVoMapper videoChapterUserVoMapper;

    @Autowired
    private AdminService adminService;


    @Override
    public List<Map<String, Object>> listVo(VideoChapterUserVo videoChapterUserVo) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<VideoChapterUserVo> vcs = videoChapterUserVoMapper.selectList(videoChapterUserVo);

        vcs.forEach(vc -> {
            ChapterResource cr = adminService.getChapterResourceById(String.valueOf(vc.getVideoChapter().getResourceChapterId()));
            Chapter chapter = adminService.getChapterById(cr.getCid());
            Recourse r = adminService.getRecourseById(cr.getRid());
            SysUser user = adminService.getUserById(String.valueOf(vc.getVideoChapterUser().getUserId()));

            Map<String, Object> object = new HashMap<>();
            object.put("videoChapter", vc.getVideoChapter());
            object.put("videoChapterUser", vc.getVideoChapterUser());
            object.put("chapterName", chapter.getName());
            object.put("resourceName", cr.getName());
            object.put("studentName", user.getUserName());

            result.add(object);
        });

        return result;
    }

    @Override
    public List<Map<String, Object>> listVo(int userId) {
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("order_by", "update_time asc");
        VideoChapterUser vcu = new VideoChapterUser();
        vcu.setUserId(userId);
        vcu.setParams(params);
        List<VideoChapterUser> videoChapterUsers = videoChapterUserMapper.selectList(vcu);

        if (!CollectionUtils.isEmpty(videoChapterUsers)) {
            videoChapterUsers.forEach(c -> {
                VideoChapter vc = videoChapterMapper.selectById(c.getVideoChapterId());
                ChapterResource cr = null;
                Chapter chapter = null;
                Recourse r = null;
                Course course = null;

                if (vc != null) {
                     cr = adminService.getChapterResourceById(String.valueOf(vc.getResourceChapterId()));
                    if (cr != null) {
                        chapter = adminService.getChapterById(cr.getCid());
                        r = adminService.getRecourseById(cr.getRid());
                        if (chapter != null) {
                            course = adminService.getCourseById(chapter.getCid());
                        }
                    }
                }

                Map<String, Object> object = new HashMap<>();
                object.put("id", c.getId());
                object.put("userId", c.getUserId());
                object.put("videoChapterId", c.getVideoChapterId());
                object.put("progress", c.getProgress());
                object.put("lastTime", c.getLastTime());
                object.put("resourceChapterId", vc.getResourceChapterId());
                if (cr != null) {
                    object.put("videoTitle", cr.getName());
                } else {
                    object.put("videoTitle", "该章节视频已不存在");
                }
                if (r != null) {
                    object.put("fileId", r.getAttrId());
                }
                if (chapter != null) {
                    object.put("chapterName", chapter.getName());
                } else {
                    object.put("chapterName", "查询不到该资源对应章节");
                }
                if (course != null) {
                    object.put("courseName", course.getName());
                } else {
                    object.put("courseName", "查询不到该资源对应课程");
                }
                result.add(object);
            });
        }

        return result;
    }

    @Override
    public VideoChapterUser selectByVideoName(VideoChapterUser videoChapterUser) {
        return videoChapterUserMapper.selectByVideoName(videoChapterUser);
    }
}
