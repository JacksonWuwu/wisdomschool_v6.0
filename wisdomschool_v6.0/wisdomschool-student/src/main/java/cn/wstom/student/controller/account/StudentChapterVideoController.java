package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.ChapterResource;
import cn.wstom.student.entity.VideoChapter;
import cn.wstom.student.entity.VideoChapterUser;
import cn.wstom.student.service.VideoChapterService;
import cn.wstom.student.service.VideoChapterUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/Student/chapter/video")
public class StudentChapterVideoController extends BaseController {
    @Autowired
    private VideoChapterUserService videoUserService;

    @Autowired
    private VideoChapterService videoChapterService;


 //   @ApiOperation("章节视频信息")
    @ResponseBody
    @GetMapping(value = "/info/{resourceChapterId}")
    public VideoChapterUser info(@PathVariable("resourceChapterId") String rcId) throws Exception {
        ChapterResource chapterResource= adminService.getChapterResourceById(String.valueOf(rcId));
        Map<String, Object> map = new HashMap<>();
        VideoChapter vc = videoChapterService.selectByRcId(Integer.valueOf(rcId));
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
            u.setVideoname(chapterResource.getName()+".mp4");
            videoUserService.update(u);
            Long lastTime= u.getLastTime();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(lastTime);
            u.setLastTime(seconds);
            System.out.println("videoInfo1===="+u);
            return u;
        } else {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            vcu.setId(uuid);
            vcu.setCreateId(Integer.valueOf(getUserId()));
            vcu.setVideoChapterId(Integer.valueOf(vc.getId()));
            vcu.setState(vc.getState());
            vcu.setVideoname(chapterResource.getName()+".mp4");
            videoUserService.save(vcu);
            System.out.println("videoInfo2===="+vcu);
            return vcu;
        }
    }

   // @ApiOperation("提交观看记录")
    @PostMapping(value = "/push")
    @ResponseBody
    public Data push(@RequestParam String id, @RequestParam String videoname, @RequestParam String lastTime) throws Exception {
        System.out.println("videoUser"+id+videoname+lastTime);
        lastTime= StringUtils.substringBefore(lastTime, ".");
        long second =  Long.valueOf(lastTime).longValue();
        VideoChapterUser videoChapterUser=new VideoChapterUser();
        videoChapterUser.setId(id);
        long millisecond = TimeUnit.SECONDS.toMillis(second);
        videoChapterUser.setLastTime(millisecond);
        videoChapterUser.setVideoname(videoname);
        if (!videoname.contains(".mp4")){
            videoChapterUser.setVideoname(videoname+".mp4");
        };
        VideoChapterUser index = videoUserService.selectByVideoName(videoChapterUser);
        System.out.println("videoUser4"+index);
        long maxProgress=0;
        maxProgress = Math.max(index.getProgress(), millisecond);
        videoChapterUser.setProgress(maxProgress);
        videoChapterUser.setUpdateTime(new Date());
        videoChapterUser.setUpdateId(Integer.valueOf(getUserId()));
        System.out.println("videoUser3"+videoChapterUser);
        return toAjax(videoUserService.update(videoChapterUser));

    }
}
