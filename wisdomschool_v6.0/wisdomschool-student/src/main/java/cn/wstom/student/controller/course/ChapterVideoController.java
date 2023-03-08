package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.ChapterResource;
import cn.wstom.student.entity.VideoChapter;
import cn.wstom.student.entity.VideoChapterUser;
import cn.wstom.student.service.VideoChapterService;
import cn.wstom.student.service.VideoChapterUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chapter/video")
public class ChapterVideoController extends BaseController {
    @Autowired
    private VideoChapterUserService videoUserService;

    @Autowired
    private VideoChapterService videoChapterService;



    @ApiOperation("章节视频信息")
    @GetMapping(value = "/info/{resourceChapterId}")
    public Data info(@PathVariable("resourceChapterId") int rcId) throws Exception {
        ChapterResource chapterResource= adminService.getChapterResourceById(String.valueOf(rcId));
        Map<String, Object> map = new HashMap<>();
        VideoChapter vc = videoChapterService.selectByRcId(rcId);
        System.out.println("vc===="+vc);
        VideoChapterUser vcu = new VideoChapterUser();
        vcu.setUserId(Integer.valueOf(getUserId()));
        vcu.setVideoname(chapterResource.getName()+".mp4");
        List<VideoChapterUser> list = videoUserService.list(vcu);
        System.out.println("list123"+list);
        if (!list.isEmpty()) {
            VideoChapterUser u = list.get(0);
            u.setVideoChapterId(Integer.valueOf(vc.getId()));
            u.setState(vc.getState());
            u.setVideoname(chapterResource.getName());
            videoUserService.update(u);
            map.put("videoInfo", u);
            System.out.println("videoInfo1===="+u);
            return Data.success(map);
        } else {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            vcu.setId(uuid);
            vcu.setCreateId(Integer.valueOf(getUserId()));
            vcu.setVideoChapterId(Integer.valueOf(vc.getId()));
            vcu.setState(vc.getState());
            vcu.setVideoname(chapterResource.getName()+".mp4");
            videoUserService.save(vcu);
            System.out.println("videoInfo2===="+vcu);
            map.put("videoInfo", vcu);
            return Data.success(map);
        }
    }

    @ApiOperation("章节视频信息")
    @GetMapping(value = "/infolocal/{videoname}")
    public Data infoLocal(@PathVariable("videoname") String videoname) throws Exception {
        Map<String, Object> map = new HashMap<>();
        VideoChapterUser vcu = new VideoChapterUser();
        vcu.setUserId(Integer.valueOf(getUserId()));
        vcu.setVideoname(videoname);
        List<VideoChapterUser> list = videoUserService.list(vcu);
        System.out.println("list12356"+list);
        if (!list.isEmpty()) {
            VideoChapterUser u = list.get(0);
            map.put("videoInfo", u);
            return Data.success(map);
        } else {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            vcu.setId(uuid);
            vcu.setCreateId(Integer.valueOf(getUserId()));
            videoUserService.save(vcu);
            map.put("videoInfo", vcu);
            return Data.success(map);
        }
    }

    @ApiOperation("章节视频信息")
    @GetMapping(value = "/list/{resourceChapterId}")
    public Data infolist(@PathVariable("resourceChapterId") int rcId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        VideoChapter vc = videoChapterService.selectByRcId(rcId);
        List<VideoChapter> list = videoChapterService.list(vc);
        if (!list.isEmpty()) {
            VideoChapter u = list.get(0);
            map.put("videoInfo", u);
            return Data.success(map);
        } else {
            vc.setCreateId(Integer.valueOf(getUserId()));
            videoChapterService.save(vc);
            map.put("videoInfo", vc);
            return Data.success(map);
        }
    }


    @ApiOperation("提交观看记录")
    @PostMapping(value = "/push")
    public Data push(@RequestBody VideoChapterUser videoUser) throws Exception {
        System.out.println("videoUser"+videoUser);
        if (!videoUser.getVideoname().contains(".mp4")){
           videoUser.setVideoname(videoUser.getVideoname()+".mp4");
        };
        VideoChapterUser index = videoUserService.selectByVideoName(videoUser);
        long maxProgress=0;
        maxProgress = Math.max(index.getProgress(), videoUser.getProgress());
        videoUser.setProgress(maxProgress);
        videoUser.setUpdateTime(new Date());
        videoUser.setUpdateId(Integer.valueOf(getUserId()));
        System.out.println("videoUser3"+videoUser);
        return toAjax(videoUserService.update(videoUser));
    }


    @ApiOperation("观看记录")
    @GetMapping(value = "/history")
    public Data history(@RequestParam("pageNum") String pageNum) {
        startPage();
        return Data.success(videoUserService.listVo(Integer.parseInt(getUserId())));
    }
}
