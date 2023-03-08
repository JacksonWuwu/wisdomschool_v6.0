package cn.wstom.admin.controller.school.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
* 用户观看 信息操作处理
*
* @author dws
* @date 20200204
*/
@Controller
@RequestMapping("/teacher/videoChapterUser")
public class VideoChapterUserController extends BaseController {
    private String prefix = "school/teacher/video";

    @Autowired
    private VideoChapterService videoChapterService;
    @Autowired
    private VideoChapterUserService videoChapterUserService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private RecourseService recourseService;
    @Autowired
    private ChapterResourceService chapterResourceService;

    @RequiresPermissions("teacher:chapter:view")

    @GetMapping("/showDetail")
    public String toList(@RequestParam("id") String userId, @RequestParam("tcId") String tcId, ModelMap modelMap) {
        modelMap.put("userId", userId);
        modelMap.put("tcid", tcId);
        return "school/teacher/statis/video";
    }

    /**
    * 查询用户观看列表
    */
    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/list/{tcid}/{userId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("tcid") String tcId, @PathVariable("userId") String userId, VideoChapterUserVo vo) throws Exception{
        SysUser stu = null;

        try {
            stu = sysUserService.getById(userId);
        } catch (Exception e) {
            throw new Exception("未找到该学生用户");
        }

        VideoChapter vc = new VideoChapter();
        vc.setCourseTeacherId(Integer.valueOf(tcId));
        System.out.println("tcId====="+tcId);
        startPage();
        List<VideoChapter> vcs = videoChapterService.list(vc);
        if (!CollectionUtils.isEmpty(vcs)) {
            SysUser finalStu = stu;
            vcs.forEach(v -> {
                ChapterResource cr = chapterResourceService.getById(String.valueOf(v.getResourceChapterId()));
                Chapter chapter = null;
                Recourse recourse = null;
                if (cr != null) {
                    chapter = chapterService.getById(cr.getCid());
                    recourse = recourseService.getById(cr.getRid());
                }

                VideoChapterUser vcu = new VideoChapterUser();
                vcu.setVideoChapterId(Integer.valueOf(v.getId()));
                System.out.println("VideoChapterId2====="+v.getId());
                vcu.setUserId(Integer.valueOf(userId));

                Map<String, Object> params = new HashMap<>();

                List vcus = videoChapterUserService.list(vcu);
                if (!vcus.isEmpty()) {
                    params.put("videoChapterUser", vcus.get(0));
                    System.out.println("???????"+vcus.get(0));
                } else {
                    params.put("videoChapterUser", null);
                }
                if (chapter != null) {
                    params.put("chapterName", chapter.getName());
                } else {
                    params.put("chapterName", "未找到章节");
                }
                if (recourse != null) {
                    params.put("resourceName", recourse.getName());
                } else {
                    params.put("resourceName", "未找到资源");
                }

                if (finalStu != null) {
                    params.put("studentName", finalStu.getUserName());
                }
                v.setParams(params);
            });
        }

        return wrapTable(vcs);
    }

    /**
    * 修改用户观看
    */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
    VideoChapterUser videoChapterUser = videoChapterUserService.getById(id);
    mmap.put("videoChapterUser", videoChapterUser);
    return prefix + "/edit";
    }

    /**
    * 修改保存用户观看
    */
    @RequiresPermissions("teacher:chapter:edit")
    @Log(title = "用户观看")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(VideoChapterUser videoChapterUser) throws Exception {
    return toAjax(videoChapterUserService.update(videoChapterUser));
    }

    /**
    * 删除用户观看
    */
    @RequiresPermissions("teacher:course:view")
    @Log(title = "用户观看")
    @PostMapping( "/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(videoChapterUserService.removeById(ids));
    }
}
